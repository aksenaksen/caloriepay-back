package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CalorieScoreHistoryRepository extends JpaRepository<CalorieScoreHistory, Long> , CustomCalorieScoreHistoryRepository {
    Optional<CalorieScoreHistory> findByUserIdAndDate(Long userId, LocalDate date);
    Optional<CalorieScoreHistory> findTopByUserIdOrderByDateDesc(Long userId);


}
