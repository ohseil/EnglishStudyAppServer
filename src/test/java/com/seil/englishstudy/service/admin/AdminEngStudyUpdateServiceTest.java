package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.request.AdminEngStudyUpdateRequest;
import com.seil.englishstudy.dto.response.AdminEngStudyReadResponse;
import com.seil.englishstudy.dto.response.AdminEngStudyUpdateResponse;
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
public class AdminEngStudyUpdateServiceTest {

    @InjectMocks
    private AdminEngStudyUpdateService adminEngStudyUpdateService;

    @Mock
    private EngStudyDataRepository engStudyDataRepository;

    @Test
    void 영어공부데이터수정() {

        // given
        final Long engStudyDataId = 1L;

        final AdminEngStudyUpdateRequest adminEngStudyUpdateRequest = AdminEngStudyUpdateRequest.builder()
                                                                                                .categoryCode(1)
                                                                                                .question("updated question")
                                                                                                .answer("updated answer")
                                                                                                .build();

        final EngStudyData foundEngStudyData = EngStudyData.builder()
                                                            .id(engStudyDataId)
                                                            .categoryCode(1)
                                                            .question("question")
                                                            .answer("answer")
                                                            .build();

        final AdminEngStudyUpdateResponse adminEngStudyUpdateResponse = AdminEngStudyUpdateResponse.builder()
                                                                                                    .categoryCode(1)
                                                                                                    .question("question")
                                                                                                    .answer("answer")
                                                                                                    .build();

        given(engStudyDataRepository.findById(engStudyDataId))
                .willReturn(Optional.of(foundEngStudyData));

        // when
        final AdminEngStudyUpdateResponse result = adminEngStudyUpdateService.updateEngStudyData(engStudyDataId, adminEngStudyUpdateRequest);

        // then
        assertThat(result).isEqualTo(adminEngStudyUpdateResponse);
    }
}
