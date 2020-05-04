package com.seil.englishstudy.service;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import com.seil.englishstudy.web.rest.model.DataCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class StudyDataCreateService {

    private EnglishStudyDataRepository englishStudyDataRepository;

    public StudyDataCreateService(EnglishStudyDataRepository englishStudyDataRepository) {
        this.englishStudyDataRepository = englishStudyDataRepository;
    }

    public DataCUDResponse createStudyData(DataCreateRequest request) {

        EnglishStudyData data = EnglishStudyData.builder().
                categorycode(request.getCategoryCode()).
                question(request.getQuestion()).
                answer(request.getAnswer()).
                build();

        englishStudyDataRepository.save(data);

        return DataCUDResponse.builder().msg("success").build();
    }
}
