package com.seil.englishstudy.service.admin;

import com.seil.englishstudy.entity.EngStudyData;
import com.seil.englishstudy.repository.EngStudyDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AdminEngStudyDeleteServiceTest {

    @InjectMocks
    private AdminEngStudyDeleteService adminEngStudyDeleteService;

    @Mock
    private EngStudyDataRepository engStudyDataRepository;

    @Test
    void 영어공부데이터삭제() {

        // given
        final Long engStudyDataId = 1L;

        final EngStudyData foundEngStudyData = EngStudyData.builder()
                                                            .id(engStudyDataId)
                                                            .categoryCode(1)
                                                            .question("question")
                                                            .answer("answer")
                                                            .build();

        given(engStudyDataRepository.findById(engStudyDataId))
                .willReturn(Optional.of(foundEngStudyData));

        // when
        adminEngStudyDeleteService.deleteEngStudyData(engStudyDataId);

        // then

    }
}
