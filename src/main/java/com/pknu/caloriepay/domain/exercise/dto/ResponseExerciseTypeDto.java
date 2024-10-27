package com.pknu.caloriepay.domain.exercise.dto;

import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseExerciseTypeDto {

    private Long id;

    private String name;

    private double averageCaloriesPerMinute;

    public static ResponseExerciseTypeDto fromEntity(ExerciseType exerciseType){
        return ResponseExerciseTypeDto.builder()
                .id(exerciseType.getId())
                .name(exerciseType.getName())
                .averageCaloriesPerMinute(exerciseType.getAverageCaloriesPerMinute())
                .build();
    }
}
