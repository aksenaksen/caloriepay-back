package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.DailyCalorieChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyCalorieChangeRepository extends JpaRepository<DailyCalorieChange, Long> {
    Optional<DailyCalorieChange> findByUserId(Long userId);
}
