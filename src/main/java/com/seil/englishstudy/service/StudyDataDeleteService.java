package com.seil.englishstudy.service;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import org.springframework.http.HttpStatus;
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

        findData.orElseThrow(() -> new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data."));

        findData.ifPresent(deleteData -> {
           englishStudyDataRepository.delete(deleteData);
        });

        return DataCUDResponse.builder().msg("success").build();
    }

    public DataCUDResponse deleteStudyDataAll() {
        englishStudyDataRepository.deleteAll();
        englishStudyDataRepository.flush();
        return DataCUDResponse.builder().msg("success").build();
    }
}
