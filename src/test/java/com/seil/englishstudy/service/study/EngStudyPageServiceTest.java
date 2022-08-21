package com.seil.englishstudy.service.study;

import com.seil.englishstudy.dto.response.EngStudyPageResponse;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EngStudyPageServiceTest {

    @InjectMocks
    private EngStudyPageService engStudyPageService;

    @Mock
    private EngStudyDataRepository engStudyDataRepository;

    @Test
    void 영어공부페이지조회() {

        // given
        final long categoryCode = 1;

        final List<EngStudyData> foundEngStudyDataList = Arrays.asList(
                EngStudyData.builder()
                        .id(1L)
                        .categoryCode(categoryCode)
                        .question("question")
                        .answer("answer")
                        .build()
        );

        final List<EngStudyPageResponse> engStudyPageResponseList = Arrays.asList(
                EngStudyPageResponse.builder()
                        .engStudyDataId(1L)
                        .categoryCode(categoryCode)
                        .question("question")
                        .answer("answer")
                        .build()
        );

        given(engStudyDataRepository.findByCategoryCode(categoryCode))
                .willReturn(foundEngStudyDataList);

        // when
        List<EngStudyPageResponse> result = engStudyPageService.getEngStudyPage(categoryCode);

        // then
        assertThat(result).isEqualTo(engStudyPageResponseList);
    }
}
