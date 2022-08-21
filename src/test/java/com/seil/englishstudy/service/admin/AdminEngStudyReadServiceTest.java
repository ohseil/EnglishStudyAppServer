package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.response.AdminEngStudyReadResponse;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AdminEngStudyReadServiceTest {

    @InjectMocks
    AdminEngStudyReadService adminEngStudyReadService;

    @Mock
    private EngStudyDataRepository engStudyDataRepository;

    @Test
    void 영어공부데이터id로조회() {

        // given
        final Long engStudyDataId = 1L;

        final EngStudyData foundEngStudyData = EngStudyData.builder()
                                                            .id(engStudyDataId)
                                                            .categoryCode(1)
                                                            .question("question")
                                                            .answer("answer")
                                                            .build();

        final AdminEngStudyReadResponse adminEngStudyReadResponse = AdminEngStudyReadResponse.builder()
                                                                                            .engStudyDataId(engStudyDataId)
                                                                                            .categoryCode(1)
                                                                                            .question("question")
                                                                                            .answer("answer")
                                                                                            .build();

        given(engStudyDataRepository.findById(engStudyDataId))
                .willReturn(Optional.of(foundEngStudyData));

        // when
        final AdminEngStudyReadResponse result = adminEngStudyReadService.readEngStudyById(engStudyDataId);

        // then
        assertThat(result).isEqualTo(adminEngStudyReadResponse);
    }
}
