package com.seil.englishstudy.service;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import com.seil.englishstudy.web.rest.model.DataUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class StudyDataUpdateServiceTest {

    @InjectMocks
    private StudyDataUpdateService studyDataUpdateService;

    @Mock
    private EnglishStudyDataRepository englishStudyDataRepository;

    @Test
    void 데이터수정() {

        // given
        final Long id = 1L;

        final EnglishStudyData data = EnglishStudyData.builder()
                                                .id(id)
                                                .categorycode(1)
                                                .question("question")
                                                .answer("answer")
                                                .build();
        final DataUpdateRequest request = DataUpdateRequest.builder()
                                                    .id(id)
                                                    .categoryCode(1)
                                                    .question("question")
                                                    .answer("answer")
                                                    .build();

        given(englishStudyDataRepository.findById(id))
                .willReturn(Optional.of(data));

        // when
        final DataCUDResponse result = studyDataUpdateService.updateStudyData(id, request);

        // then
        assertThat(result.getMsg())
                .isEqualTo("success");
    }
}
