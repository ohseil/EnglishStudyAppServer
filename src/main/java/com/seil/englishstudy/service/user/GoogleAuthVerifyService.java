package com.seil.englishstudy.service.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.SigninFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthVerifyService {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public GoogleAuthVerifyService() {
        googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("919073491758-fe2t234io7anmb07cju1hcg1r96r1jp2.apps.googleusercontent.com"))
                .build();
    }

    public GoogleIdToken getGoogleIdToken(final String idToken) {

        try {
            return GoogleIdToken.parse(new GsonFactory(), idToken);
        } catch(IOException ex) {
            throw new SigninFailedException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "failed verified.");
        }

    }

    public boolean verifyIdToken(final GoogleIdToken googleIdToken) {

        try {
            return googleIdTokenVerifier.verify(googleIdToken);
        } catch(IOException ex) {
            throw new SigninFailedException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "failed verified.");
        } catch(GeneralSecurityException ex) {
            throw new SigninFailedException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "failed verified.");
        }

    }
}
