package com.seil.englishstudy.service;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyDataDeleteService {

    private final EnglishStudyDataRepository englishStudyDataRepository;

    public StudyDataDeleteService(EnglishStudyDataRepository englishStudyDataRepository) {
        this.englishStudyDataRepository = englishStudyDataRepository;
    }

    public DataCUDResponse deleteStudyData(Long id) {

        Optional<EnglishStudyData> findData = englishStudyDataRepository.findById(id);

        findData.ifPresent(deleteData -> {
           englishStudyDataRepository.delete(deleteData);
        });

        return DataCUDResponse.builder().msg("success").build();
    }
}
