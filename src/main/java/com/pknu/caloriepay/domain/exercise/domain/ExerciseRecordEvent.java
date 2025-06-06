package com.pknu.caloriepay.domain.exercise.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExerciseRecordEvent {
    private Long userId;
    private double calorie;
}
