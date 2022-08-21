package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.request.AdminEngStudyUpdateRequest;
import com.seil.englishstudy.dto.response.AdminEngStudyUpdateResponse;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminEngStudyUpdateService {

    private final EngStudyDataRepository engStudyDataRepository;

    @Transactional
    public AdminEngStudyUpdateResponse updateEngStudyData(final Long engStudyDataId, final AdminEngStudyUpdateRequest adminEngStudyUpdateRequest) {

        Optional<EngStudyData> engStudyData = engStudyDataRepository.findById(engStudyDataId);

        engStudyData.orElseThrow(() -> new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data."));

        AdminEngStudyUpdateResponse adminEngStudyUpdateResponse = AdminEngStudyUpdateResponse.builder()
                .categoryCode(engStudyData.get().getCategoryCode())
                .question(engStudyData.get().getQuestion())
                .answer(engStudyData.get().getAnswer())
                .build();

        EngStudyData updatedData = EngStudyData.builder()
                .id(engStudyData.get().getId())
                .categoryCode(adminEngStudyUpdateRequest.getCategoryCode())
                .question(adminEngStudyUpdateRequest.getQuestion())
                .answer(adminEngStudyUpdateRequest.getAnswer())
                .build();

        engStudyDataRepository.save(updatedData);

        return adminEngStudyUpdateResponse;
    }
}
