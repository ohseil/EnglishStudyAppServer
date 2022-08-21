package com.seil.englishstudy.repository;

import com.seil.englishstudy.entity.EngStudyData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngStudyDataRepository extends JpaRepository<EngStudyData, Long> {

    List<EngStudyData> findByCategoryCode(final long categoryCode);
}
