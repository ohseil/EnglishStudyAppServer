package com.seil.englishstudy.service;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataReadPKResponse;
import com.seil.englishstudy.web.rest.model.DataReadResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class StudyDataReadServiceTest {

    @InjectMocks
    private StudyDataReadService studyDataReadService;

    @Mock
    private EnglishStudyDataRepository englishStudyDataRepository;

    @Test
    void pk조회() {

        // given
        final long categoryCode = 1;
        final String question = "question";
        final String answer = "answer";

        final EnglishStudyData data = EnglishStudyData.builder()
                                                .id(1L)
                                                .categorycode(1)
                                                .question("question")
                                                .answer("answer")
                                                .build();

        given(englishStudyDataRepository.findByCategorycodeAndQuestionAndAnswer(categoryCode, question, answer))
                .willReturn(data);

        // when
        final DataReadPKResponse result = studyDataReadService.readPK(categoryCode, question, answer);

        // then
        assertThat(result.getPk())
                .isEqualTo(1);
    }

    @Test
    void 모든데이터조회() {

        // given
        final List<EnglishStudyData> dataList = Arrays.asList(
                new EnglishStudyData(1L, 1, "question", "amswer")
        );

        given(englishStudyDataRepository.findAll())
                .willReturn(dataList);

        // when
        final DataReadResponse result = studyDataReadService.readStudyDataAll();

        // then
        assertThat(result.getDataList())
                .isEqualTo(dataList);
    }

    @Test
    void 카테고리코드로데이터조회() {

        // given
        final Long categoryCode = 1L;

        final List<EnglishStudyData> dataList = Arrays.asList(
                new EnglishStudyData(1L, 1, "question", "answer")
        );

        given(englishStudyDataRepository.findByCategorycode(categoryCode))
                .willReturn(dataList);

        // when
        final DataReadResponse result = studyDataReadService.readStudyDataByCategory(categoryCode);

        // then
        assertThat(result.getDataList())
                .isEqualTo(dataList);
    }

    @Test
    void ids로데이터여러개조회() {

        // given
        final List<Long> idList = Arrays.asList(1L, 2L);

        final List<EnglishStudyData> dataList = Arrays.asList(
                new EnglishStudyData(1L, 1, "question", "answer"),
                new EnglishStudyData(2L, 1, "question", "answer")
        );

        given(englishStudyDataRepository.findByIdIn(idList))
                .willReturn(dataList);

        // when
        final DataReadResponse result = studyDataReadService.readStudyDataByIds(idList);

        // then
        assertThat(result.getDataList())
                .isEqualTo(dataList);
    }
}
