package com.pknu.caloriepay.domain.calender.application.out;

import com.pknu.caloriepay.concept.ExerciseDetail;
import com.pknu.caloriepay.concept.ExerciseInfo;
import com.pknu.caloriepay.domain.exercise.domain.Duration;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;

import java.time.LocalDate;

public record CalendarExerciseResponse(
        Long id,
        Long userId,
        String title,
        String exerciseTypeName,
        Duration duration,
        double caloriesBurned,
        LocalDate date
) {
    public static CalendarExerciseResponse from(Exercise exerciseRecord, ExerciseType type){
        return new CalendarExerciseResponse(
                exerciseRecord.getId(),
                exerciseRecord.getUserId(),
                exerciseRecord.getTitle(),
                type.getName(),
                exerciseRecord.getDuration(),
                exerciseRecord.getCaloriesBurned(),
                exerciseRecord.getDate()
        );
    }

    public static CalendarExerciseResponse of(ExerciseInfo exerciseInfo, ExerciseDetail exerciseDetail) {
        return new CalendarExerciseResponse(
                exerciseInfo.id(),
                exerciseInfo.userId(),
                exerciseInfo.title(),
                exerciseDetail.exerciseName(),
                exerciseDetail.duration(),
                exerciseDetail.burnedCalorie(),
                exerciseInfo.date()
        );
    }
}
