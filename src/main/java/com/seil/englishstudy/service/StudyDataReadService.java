package com.seil.englishstudy.service;

import com.google.api.client.googleapis.apache.v2.GoogleApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import com.seil.englishstudy.web.rest.model.DataReadPKResponse;
import com.seil.englishstudy.web.rest.model.DataReadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

@Service
public class StudyDataReadService {

    private final EnglishStudyDataRepository englishStudyDataRepository;

    String tokenString = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijc3NDU3MzIxOGM2ZjZhMmZlNTBlMjlhY2JjNjg2NDMyODYzZmM5YzMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MTkwNzM0OTE3NTgtYzcxZnM2NHQ2dmwyMjk2bDIyYzUyNzlzYXZyOXBvMGguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MTkwNzM0OTE3NTgtZmUydDIzNGlvN2FubWIwN2NqdTFoY2cxcjk2cjFqcDIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDU1NTMzMzc4NjQ2MzA1MDIwNDMiLCJlbWFpbCI6InNpb2hiYXJhbUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IuyYpOyEuOydvCIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vLWQzVXY4MVEwM3RFL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y253eWFZNV9FUU1fTmgtbnM3ek9JMnBoMUpYclEvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IuyEuOydvCIsImZhbWlseV9uYW1lIjoi7JikIiwibG9jYWxlIjoia28iLCJpYXQiOjE2MTgzMjc4NzAsImV4cCI6MTYxODMzMTQ3MH0.ZB4EgDvIdZgm_RAzriHKJD17WFER8ep8L7-bMy1uh_0VfTK1xUb5DDbMxEClFTR6wdL47Dh58mEmZTRNFePxUAgiosGerMBUiKwSZELNzQiLY4JnVxLp1eaZ-pTwsp-P3G7a94LvNx7bzKaL-aNBeIjTkuKqjUCPXRd_fOqHhwoHHjQHaDxyZyDnC4rLzqLJE0_tbhFCh8TeKoN9pnI6hERLSj4Uzee46d_5K12y7TtMPdFJ49j8CRxMUUVXrUYpDqmW3gsp-wa4i9oOgp8rVrGCw2s1JJ2Kgrweup-e_2F5vzo6rCJa5AxdTXaqwebGfZ7EZBXjfloJZIVX4iMoJA";

    public StudyDataReadService(EnglishStudyDataRepository englishStudyDataRepository) {
        this.englishStudyDataRepository = englishStudyDataRepository;
    }

    public DataReadPKResponse readPK(long categoryCode, String question, String answer) {
        EnglishStudyData data = englishStudyDataRepository.findByCategorycodeAndQuestionAndAnswer(categoryCode, question, answer);
        if (data == null)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "데이터가 없습니다.");
        return DataReadPKResponse.builder().
                pk(data.getId()).
                build();
    }

    public DataReadResponse readStudyDataAll() {

        /*GoogleIdToken googleIdToken;
        GoogleIdTokenVerifier mVerifier;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory mJFactory = new GsonFactory();
        
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("919073491758-fe2t234io7anmb07cju1hcg1r96r1jp2.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();


        try {
            GoogleIdToken token = GoogleIdToken.parse(mJFactory, tokenString);

            if (verifier.verify(token)) {
                System.out.println("valid token -> " + token.getPayload().toString());
            }
            else {
                System.out.println("invalid token.");
            }
        } catch(IOException ex) {
            System.out.println("io exeption => " + ex.getMessage());
        } catch(GeneralSecurityException gse) {
            System.out.println("general security exeption => " + gse.getMessage());
        }*/

        List<EnglishStudyData> dataList = englishStudyDataRepository.findAll();
        if (dataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "데이터가 없습니다.");
        return DataReadResponse.builder().
                dataList(dataList).
                build();
    }

    public DataReadResponse readStudyDataByCategory(long categoryCode) {
        List<EnglishStudyData> dataList = englishStudyDataRepository.findByCategorycode(categoryCode);
        if (dataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "데이터가 없습니다.");
        return DataReadResponse.builder().
                dataList(dataList).
                build();
    }

    public DataReadResponse readStudyDataByIds(List<Long> ids) {
        List<EnglishStudyData> dataList = englishStudyDataRepository.findByIdIn(ids);
        if (dataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "데이터가 없습니다.");
        return DataReadResponse.builder().
                dataList(dataList).
                build();
    }
}
