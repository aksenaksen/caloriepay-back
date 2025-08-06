package com.pknu.caloriepay.domain.score.application.out;

import com.pknu.caloriepay.domain.recommandcalorie.domain.RecommandCalorie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDailyCalorieChangeDto {

    private Long id;

    private Long userId;

    private double dailyRecommendedCalorie;

    private double remainCalorie;

    private double totalCalorie;


    public static ResponseDailyCalorieChangeDto fromEntity(RecommandCalorie calorieChange){
        return ResponseDailyCalorieChangeDto.builder()
                .id(calorieChange.getId())
                .userId(calorieChange.getUserId())
                .dailyRecommendedCalorie(calorieChange.getDailyRecommendedCalorie())
                .remainCalorie(calorieChange.getRemainCalorie())
                .totalCalorie(calorieChange.getTotalCalorie())
                .build();
    }

}
