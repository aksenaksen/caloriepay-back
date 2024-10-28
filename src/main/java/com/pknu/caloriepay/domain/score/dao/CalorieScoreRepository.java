package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CalorieScoreRepository extends JpaRepository<CalorieScore, Long> {
    Optional<CalorieScore> findByUserIdAndDate(Long userId, LocalDate date);
}
