package com.seil.englishstudy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class EngStudyDataRepositoryTest {

    /*@Autowired
    private EnglishStudyDataRepository englishStudyDataRepository;

    @Test

    void 데이터저장() {

        // given
        final EnglishStudyData data = EnglishStudyData.builder()
                                            .categorycode(1)
                                            .question("question")
                                            .answer("answer")
                                            .build();

        // when
        final EnglishStudyData saved_data = englishStudyDataRepository.save(data);

        // then
        assertThat(saved_data.getId())
                .isNotNull();
        assertThat(saved_data.getCategorycode())
                .isEqualTo(data.getCategorycode());
        assertThat(saved_data.getQuestion())
                .isEqualTo("question");
        assertThat(saved_data.getAnswer())
                .isEqualTo("answer");
    }

    @Test
    void 데이터조회_by_카테고리코드() {

        // given
        long categoryCode = 1;

        final EnglishStudyData saved_data1 = englishStudyDataRepository.save(
                EnglishStudyData.builder().categorycode(1).question("question").answer("answer").build()
        );
        final EnglishStudyData saved_data2 = englishStudyDataRepository.save(
                EnglishStudyData.builder().categorycode(1).question("question1").answer("answer1").build()
        );

        englishStudyDataRepository.save(
                EnglishStudyData.builder().categorycode(2).question("question2").answer("answer2").build()
        );

        // when
        final List<EnglishStudyData> read_data = englishStudyDataRepository.findByCategorycode(categoryCode);

        // then
        assertThat(read_data)
                .contains(saved_data1, saved_data2);
    }

    @Test
    void 데이터조회_by_studydata() {

        // given
        final long categoryCode = 1;
        final String question = "question";
        final String answer = "answer";

        final EnglishStudyData saved_data = englishStudyDataRepository.save(
                EnglishStudyData.builder().categorycode(categoryCode).question(question).answer(answer).build()
        );

        // when
        final EnglishStudyData read_data = englishStudyDataRepository
                .findByCategorycodeAndQuestionAndAnswer(categoryCode, question, answer);

        // then
        assertThat(read_data)
                .isEqualTo(saved_data);
    }

    // test 실행 전 context 재실행
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void 데이터조회_by_idList() {

        // given
        final List<Long> idList = Arrays.asList(1L, 2L);

        final EnglishStudyData saved_data1 = englishStudyDataRepository.save(
                EnglishStudyData.builder().categorycode(1).question("question").answer("answer").build()
        );
        final EnglishStudyData saved_data2 = englishStudyDataRepository.save(
                EnglishStudyData.builder().categorycode(2).question("question1").answer("answer1").build()
        );

        // when
        final List<EnglishStudyData> read_data = englishStudyDataRepository.findByIdIn(idList);

        // then
        assertThat(read_data)
                .contains(saved_data1, saved_data2);
    }*/

}
