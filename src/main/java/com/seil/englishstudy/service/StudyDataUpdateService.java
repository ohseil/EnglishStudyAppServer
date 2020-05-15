package com.seil.englishstudy.service;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import com.seil.englishstudy.web.rest.model.DataUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyDataUpdateService {

    private final EnglishStudyDataRepository englishStudyDataRepository;

    public StudyDataUpdateService(EnglishStudyDataRepository englishStudyDataRepository) {
        this.englishStudyDataRepository = englishStudyDataRepository;
    }

    public DataCUDResponse updateStudyData(DataUpdateRequest request) {

        Optional<EnglishStudyData> findData = englishStudyDataRepository.findById(request.getId());

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
