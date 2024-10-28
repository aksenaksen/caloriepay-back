package com.pknu.caloriepay.domain.score.dto;

import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ResponseCalorieScoreDto {
    private Long id;

    private Long userId;

    private LocalDate date;

    private Integer score;

    public static ResponseCalorieScoreDto fromEntity(CalorieScore calorieScore) {
        return ResponseCalorieScoreDto.builder()
                .id(calorieScore.getId())
                .score(calorieScore.getScore())
                .userId(calorieScore.getUserId())
                .date(calorieScore.getDate())
                .build();
    }

}
