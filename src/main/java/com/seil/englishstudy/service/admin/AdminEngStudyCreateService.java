package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.request.AdminEngStudyCreateRequest;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminEngStudyCreateService {

    private final EngStudyDataRepository engStudyDataRepository;

    public Long createEngStudyData(AdminEngStudyCreateRequest adminEngStudyCreateRequest) {

        EngStudyData engStudyData = EngStudyData.builder()
                .categoryCode(adminEngStudyCreateRequest.getCategoryCode())
                .question(adminEngStudyCreateRequest.getQuestion())
                .answer(adminEngStudyCreateRequest.getAnswer())
                .build();

        return engStudyDataRepository.save(engStudyData).getId();
    }
}
