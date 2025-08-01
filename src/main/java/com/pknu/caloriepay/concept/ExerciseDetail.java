package com.pknu.caloriepay.concept;

import com.pknu.caloriepay.domain.exercise.domain.Duration;

public record ExerciseDetail(
        long exerciseTypeId,
        String exerciseName,
        double burnedCalorie,
        Duration duration
) { }
