package com.seil.englishstudy.repository;

import com.seil.englishstudy.entity.EnglishStudyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnglishStudyDataRepository extends JpaRepository<EnglishStudyData, Long> {

    List<EnglishStudyData> findByCategorycode(long categoryCode);
    EnglishStudyData findByCategorycodeAndQuestionAndAnswer(long categorycode, String question, String answer);
}
