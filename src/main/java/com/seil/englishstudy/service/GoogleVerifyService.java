package com.seil.englishstudy.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.seil.englishstudy.Model.GoogleProfile;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.SigninFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleVerifyService {

    GoogleIdTokenVerifyService googleIdTokenVerifyService;

    interface GoogleIdTokenVerifyService {
        void makeToken(String idToken);
        boolean verify();
        GoogleIdToken getToken();
        String getTokenPayloadString();
        String getEmail();
        String getName();
    }

    class DefaultGoogleVerifyService implements GoogleIdTokenVerifyService {

        GoogleIdTokenVerifier verifier;
        GoogleIdToken googleIdToken;

        DefaultGoogleVerifyService() {
            verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList("919073491758-fe2t234io7anmb07cju1hcg1r96r1jp2.apps.googleusercontent.com"))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();
        }

        @Override
        public void makeToken(String idToken) {
            try {
                googleIdToken = GoogleIdToken.parse(new GsonFactory(), idToken);
            } catch(IOException ex) {
                throw new SigninFailedException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "failed verified.");
            }

        }

        @Override
        public boolean verify() {
            try {
                return verifier.verify(googleIdToken);
            } catch(IOException ex) {
                throw new SigninFailedException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "failed verified.");
            } catch(GeneralSecurityException gse) {
                throw new SigninFailedException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "failed verified.");
            }
        }

        @Override
        public GoogleIdToken getToken() {
            return googleIdToken;
        }

        @Override
        public String getTokenPayloadString() {
            return googleIdToken.getPayload().toString();
        }

        @Override
        public String getEmail() {
            return googleIdToken.getPayload().getEmail();
        }

        @Override
        public String getName() {
            return googleIdToken.getPayload().get("name").toString();
        }
    }

    public GoogleVerifyService() {
        googleIdTokenVerifyService = new DefaultGoogleVerifyService();
    }

    // google의 access token을 사용하여 사용자 인증 후 프로필 정보 생성.
    public GoogleProfile getGoogleProfile(String accessToken) {

        googleIdTokenVerifyService.makeToken(accessToken);

        if (googleIdTokenVerifyService.verify() == true) {

            System.out.println("valid token -> " + googleIdTokenVerifyService.getTokenPayloadString());

            return GoogleProfile.builder()
                    .email(googleIdTokenVerifyService.getEmail())
                    .name(googleIdTokenVerifyService.getName())
                    .build();
        } else {
            throw new SigninFailedException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "not valid token.");
        }

    }
}
