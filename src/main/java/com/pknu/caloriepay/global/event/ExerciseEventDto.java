package com.pknu.caloriepay.global.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExerciseEventDto {
    private Long userId;
    private double calorie;
}
