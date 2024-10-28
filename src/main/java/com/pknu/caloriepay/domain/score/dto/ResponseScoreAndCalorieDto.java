package com.pknu.caloriepay.domain.score.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseScoreAndCalorieDto {

    private ResponseDailyCalorieChangeDto calorieChange;
    private ResponseCalorieScoreDto calorieScore;
}
