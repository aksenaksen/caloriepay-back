package com.pknu.caloriepay.domain.meal.dto;

import com.pknu.caloriepay.domain.meal.domain.Meal;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MealDto {
    private LocalDateTime mealTime;
    private int totalCalorie;
    private String mealImgUrl;
    private List<FoodDto> foods;


    public static MealDto from(Meal meal) {
        MealDto mealDto = new MealDto();
        mealDto.mealTime = meal.getMealTime();
        mealDto.totalCalorie = meal.getTotalCalorie();
        mealDto.mealImgUrl = meal.getMealImgUrl();
        mealDto.foods = meal.getFoods().stream()
                .map(FoodDto::from)  // Food 엔티티를 FoodDto로 변환
                .collect(Collectors.toList());
        return mealDto;
    }

}
