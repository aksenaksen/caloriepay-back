package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomCalorieScoreHistoryRepository {

    List<CalorieScoreHistory> findLatestScoresByUserOrderByScoreDesc(Pageable pageable);

    Optional<CalorieScoreHistory> findLatestScoreByUserIdAndYearAndMonth(Long userId, int year, int month);

    Map<String, Object> findUserRankingByUserId(Long userId);

    Optional<CalorieScoreHistory> findHighScoreOfMonthByUserId(Long userId, LocalDate start, LocalDate end);
}
