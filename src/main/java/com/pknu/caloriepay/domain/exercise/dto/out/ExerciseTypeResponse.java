package com.pknu.caloriepay.domain.exercise.dto.out;

import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import lombok.Builder;

@Builder
public record ExerciseTypeResponse(
        Long id,
        String name,
        double averageCaloriesPerMinute
) {

    public static ExerciseTypeResponse fromEntity(ExerciseType exerciseType){
        return ExerciseTypeResponse.builder()
                .id(exerciseType.getId())
                .name(exerciseType.getName())
                .averageCaloriesPerMinute(exerciseType.getAverageCaloriesPerMinute())
                .build();
    }
}
