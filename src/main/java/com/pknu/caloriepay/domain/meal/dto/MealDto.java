package com.pknu.caloriepay.domain.meal.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MealDto {
    private LocalDateTime mealTime;
    private int totalCalorie;
    private String mealImgUrl;
    private List<FoodDto> foods;

}
