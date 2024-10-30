package com.pknu.caloriepay.global.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MealEventDto {

    private Long userId;
    private double calorie;

}
