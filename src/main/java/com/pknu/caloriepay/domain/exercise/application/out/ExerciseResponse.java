package com.pknu.caloriepay.domain.exercise.application.out;

import com.pknu.caloriepay.domain.exercise.domain.Duration;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;


import java.time.LocalDate;

public record ExerciseResponse(
        long id,
        long userId,
        String title,
        String exerciseName,
        Duration duration,
        double calorieBurned,
        LocalDate date
) {
    public static ExerciseResponse from(Exercise exerciseRecord, ExerciseType type){
        return new ExerciseResponse(
                exerciseRecord.getId(),
                exerciseRecord.getUserId(),
                exerciseRecord.getTitle(),
                type.getName(),
                exerciseRecord.getDuration(),
                exerciseRecord.getCaloriesBurned(),
                exerciseRecord.getDate()
        );
    }
}
