package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.response.AdminEngStudyReadResponse;
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
public class AdminEngStudyReadService {

    private final EngStudyDataRepository engStudyDataRepository;

    @Transactional(readOnly = true)
    public AdminEngStudyReadResponse readEngStudyById(final Long engStudyDataId) {

        Optional<EngStudyData> engStudyData = engStudyDataRepository.findById(engStudyDataId);

        engStudyData.orElseThrow(() -> new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data."));

        return AdminEngStudyReadResponse.builder()
                .engStudyDataId(engStudyData.get().getId())
                .categoryCode(engStudyData.get().getCategoryCode())
                .question(engStudyData.get().getQuestion())
                .answer(engStudyData.get().getAnswer())
                .build();
    }
}
