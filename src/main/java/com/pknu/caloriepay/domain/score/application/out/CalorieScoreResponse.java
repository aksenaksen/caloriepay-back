package com.pknu.caloriepay.domain.score.application.out;

import com.pknu.caloriepay.domain.score.domain.CalorieScore;

import java.time.LocalDate;

public record CalorieScoreResponse(
        Long id,
        Long userId,
        String name,
        LocalDate date,
        Integer score
) {
    public static CalorieScoreResponse of(CalorieScore calorieScore, String name) {
        return new CalorieScoreResponse(
                calorieScore.getId(),
                calorieScore.getUserId(),
                name,
                calorieScore.getDate(),
                calorieScore.getScore()
        );
    }
}
