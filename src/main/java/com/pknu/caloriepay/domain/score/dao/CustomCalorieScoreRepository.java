package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomCalorieScoreRepository {

    List<CalorieScore> findLatestScoresByUserOrderByScoreDesc(Pageable pageable);

    Optional<CalorieScore> findLatestScoreByUserIdAndYearAndMonth(Long userId, int year, int month);

    Map<String, Object> findUserRankingByUserId(Long userId);

}
