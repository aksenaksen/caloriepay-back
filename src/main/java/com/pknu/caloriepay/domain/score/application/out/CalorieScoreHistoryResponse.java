package com.pknu.caloriepay.domain.score.application.out;

import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;

import java.time.LocalDate;

public record CalorieScoreHistoryResponse(
        Long id,
        Long userId,
        String name,
        LocalDate date,
        Integer score
) {
    public static CalorieScoreHistoryResponse of(CalorieScoreHistory calorieScore, String name) {
        return new CalorieScoreHistoryResponse(
                calorieScore.getId(),
                calorieScore.getUserId(),
                name,
                calorieScore.getDate(),
                calorieScore.getScore()
        );
    }
}
