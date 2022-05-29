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

    public StudyDataReadService(EnglishStudyDataRepository englishStudyDataRepository) {
        this.englishStudyDataRepository = englishStudyDataRepository;
    }

    public DataReadPKResponse readPK(long categoryCode, String question, String answer) {
        EnglishStudyData data = englishStudyDataRepository.findByCategorycodeAndQuestionAndAnswer(categoryCode, question, answer);
        if (data == null)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");
        return DataReadPKResponse.builder().
                pk(data.getId()).
                build();
    }

    public DataReadResponse readStudyDataAll() {

        List<EnglishStudyData> dataList = englishStudyDataRepository.findAll();
        if (dataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");
        return DataReadResponse.builder().
                dataList(dataList).
                build();
    }

    public DataReadResponse readStudyDataByCategory(long categoryCode) {
        List<EnglishStudyData> dataList = englishStudyDataRepository.findByCategorycode(categoryCode);
        if (dataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");
        return DataReadResponse.builder().
                dataList(dataList).
                build();
    }

    public DataReadResponse readStudyDataByIds(List<Long> ids) {
        List<EnglishStudyData> dataList = englishStudyDataRepository.findByIdIn(ids);
        if (dataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");
        return DataReadResponse.builder().
                dataList(dataList).
                build();
    }
}
