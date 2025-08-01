package com.pknu.caloriepay.domain.meal.application.out;

import com.pknu.caloriepay.domain.meal.domain.Meal;
import com.pknu.caloriepay.domain.meal.dto.FoodDto;
import com.pknu.caloriepay.domain.meal.dto.MealDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record MealResponse(
        LocalDateTime mealTime,
        int totalCalorie,
        String mealImgUrl,
        List<FoodDto>foods
)
{

    public static MealResponse from(Meal meal) {

        return new MealResponse(
                meal.getMealTime(),
                meal.getTotalCalorie(),
                meal.getMealImgUrl(),
                meal.getFoods().stream()
                        .map(FoodDto::from)  // Food 엔티티를 FoodDto로 변환
                        .collect(Collectors.toList())
                );
    }
}
