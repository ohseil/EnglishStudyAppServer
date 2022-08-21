package com.seil.englishstudy.service.study;

import com.seil.englishstudy.dto.response.EngStudyPageResponse;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EngStudyPageService {

    private final EngStudyDataRepository engStudyDataRepository;

    @Transactional(readOnly = true)
    public List<EngStudyPageResponse> getEngStudyPage(final long categoryCode) {

        List<EngStudyData> engStudyDataList = engStudyDataRepository.findByCategoryCode(categoryCode);

        if (engStudyDataList.isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");

        List<EngStudyPageResponse> engStudyPageResponseList = new ArrayList<>();

        engStudyDataList.forEach(engStudyData -> {
            engStudyPageResponseList.add(EngStudyPageResponse.builder()
                    .engStudyDataId(engStudyData.getId())
                    .categoryCode(engStudyData.getCategoryCode())
                    .question(engStudyData.getQuestion())
                    .answer(engStudyData.getAnswer())
                    .build());
        });

        return engStudyPageResponseList;
    }
}
