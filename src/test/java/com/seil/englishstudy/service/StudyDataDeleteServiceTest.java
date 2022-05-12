package com.seil.englishstudy.service;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.seil.englishstudy.entity.EnglishStudyData;
import com.seil.englishstudy.repository.EnglishStudyDataRepository;
import com.seil.englishstudy.web.rest.model.DataCUDResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class StudyDataDeleteServiceTest {

    @InjectMocks
    private StudyDataDeleteService studyDataDeleteService;

    @Mock
    private EnglishStudyDataRepository englishStudyDataRepository;

    @Test
    void 데이터하나삭제() {

        // given
        final Long id = 1L;

        final EnglishStudyData data = EnglishStudyData.builder()
                                                .id(1L)
                                                .categorycode(1)
                                                .question("question")
                                                .answer("answer")
                                                .build();

        given(englishStudyDataRepository.findById(id))
                .willReturn(Optional.of(data));

        // when
        final DataCUDResponse result = studyDataDeleteService.deleteStudyData(id);

        // then
        assertThat(result.getMsg())
                .isEqualTo("success");
    }

    @Test
    void 데이터모두삭제() {

        // given

        // when
        final DataCUDResponse result = studyDataDeleteService.deleteStudyDataAll();

        // then
        assertThat(result.getMsg())
                .isEqualTo("success");
    }
}
