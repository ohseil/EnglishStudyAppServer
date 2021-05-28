package com.seil.englishstudy.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.seil.englishstudy.Model.GoogleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class GoogleVerityService {

    // google의 access token을 사용하여 사용자 인증 후 프로필 정보 생성.
    public GoogleProfile getGoogleProfile(String accessToken) {

        JsonFactory mJFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("919073491758-fe2t234io7anmb07cju1hcg1r96r1jp2.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        try {

            GoogleIdToken token = GoogleIdToken.parse(mJFactory, accessToken);

            if (verifier.verify(token)) {
                System.out.println("valid token -> " + token.getPayload().toString());

                return GoogleProfile.builder()
                        .email(token.getPayload().getEmail())
                        .name(token.getPayload().get("name").toString())
                        .build();
            }
            else {
                System.out.println("invalid token.");
            }
        } catch(IOException ex) {
            System.out.println("io exeption => " + ex.getMessage());
        } catch(GeneralSecurityException gse) {
            System.out.println("general security exeption => " + gse.getMessage());
        }

        return null;
    }
}
