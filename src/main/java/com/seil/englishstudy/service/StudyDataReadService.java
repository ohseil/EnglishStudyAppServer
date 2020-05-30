package com.seil.englishstudy.service;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import com.seil.englishstudy.web.rest.model.DataReadPKResponse;
import com.seil.englishstudy.web.rest.model.DataReadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyDataReadService {

    private final EnglishStudyDataRepository englishStudyDataRepository;

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
}
