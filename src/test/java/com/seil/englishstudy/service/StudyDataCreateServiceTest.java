package com.seil.englishstudy.service;


import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import com.seil.englishstudy.web.rest.model.DataCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class StudyDataCreateServiceTest {

    @InjectMocks
    private StudyDataCreateService studyDataCreateService;

    @Mock
    private EnglishStudyDataRepository englishStudyDataRepository;

    @Test
    void 데이터생성() {

        // given
        final EnglishStudyData before_data = EnglishStudyData.builder()
                                                    .categorycode(1)
                                                    .question("question")
                                                    .answer("answer")
                                                    .build();
        final EnglishStudyData after_data = EnglishStudyData.builder()
                                                    .id(1L)
                                                    .categorycode(1)
                                                    .question("question")
                                                    .answer("answer")
                                                    .build();
        final DataCreateRequest dataCreateRequest = DataCreateRequest.builder()
                                                            .categoryCode(1)
                                                            .question("question")
                                                            .answer("answer")
                                                            .build();

        given(englishStudyDataRepository.save(before_data))
                .willReturn(after_data);

        // when
        final DataCUDResponse result = studyDataCreateService.createStudyData(dataCreateRequest);

        // then
        assertThat(result.getMsg()).isEqualTo("1");
    }

}
