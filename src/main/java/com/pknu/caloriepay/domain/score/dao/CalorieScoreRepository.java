package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CalorieScoreRepository extends JpaRepository<CalorieScore, Long> ,CustomCalorieScoreRepository{
    Optional<CalorieScore> findByUserIdAndDate(Long userId, LocalDate date);
    Optional<CalorieScore> findTopByUserIdOrderByDateDesc(Long userId);


}
