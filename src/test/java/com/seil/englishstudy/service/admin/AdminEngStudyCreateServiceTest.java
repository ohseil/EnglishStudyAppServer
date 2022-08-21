package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.dto.request.AdminEngStudyCreateRequest;
import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AdminEngStudyCreateServiceTest {

    @InjectMocks
    private AdminEngStudyCreateService adminEngStudyCreateService;

    @Mock
    private EngStudyDataRepository engStudyDataRepository;

    @Test
    void 영어공부데이터생성() {

        // given
        final Long engStudyDataId = 1L;

        final AdminEngStudyCreateRequest adminEngStudyCreateRequest = AdminEngStudyCreateRequest.builder()
                                                                                                .categoryCode(1)
                                                                                                .question("question")
                                                                                                .answer("answer")
                                                                                                .build();

        final EngStudyData engStudyData = EngStudyData.builder()
                                                    .categoryCode(1)
                                                    .question("question")
                                                    .answer("answer")
                                                    .build();

        final EngStudyData createdEngStudyData = EngStudyData.builder()
                                                            .id(engStudyDataId)
                                                            .categoryCode(1)
                                                            .question("question")
                                                            .answer("answer")
                                                            .build();

        given(engStudyDataRepository.save(engStudyData))
                .willReturn(createdEngStudyData);

        // when
        final Long result = adminEngStudyCreateService.createEngStudyData(adminEngStudyCreateRequest);

        // then
        assertThat(result).isEqualTo(engStudyDataId);
    }
}
