package com.seil.englishstudy.service;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import com.seil.englishstudy.web.rest.model.DataUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyDataUpdateService {

    private final EnglishStudyDataRepository englishStudyDataRepository;

    public StudyDataUpdateService(EnglishStudyDataRepository englishStudyDataRepository) {
        this.englishStudyDataRepository = englishStudyDataRepository;
    }

    public DataCUDResponse updateStudyData(Long id, DataUpdateRequest request) {

        Optional<EnglishStudyData> findData = englishStudyDataRepository.findById(id);

        findData.orElseThrow(() -> new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data."));

        findData.ifPresent(updateData -> {
            updateData.setCategorycode(request.getCategoryCode());
            updateData.setQuestion(request.getQuestion());
            updateData.setAnswer(request.getAnswer());
            englishStudyDataRepository.save(updateData);
            englishStudyDataRepository.flush();
        });

        return DataCUDResponse.builder().msg("success").build();
    }
}
