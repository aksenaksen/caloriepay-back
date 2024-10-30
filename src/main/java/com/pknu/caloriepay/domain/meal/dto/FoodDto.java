package com.pknu.caloriepay.domain.meal.dto;

import com.pknu.caloriepay.domain.meal.domain.Food;
import lombok.Getter;

@Getter
public class FoodDto {
    private String foodName;
    private String foodImgUrl;
    private int calorie;
    private double protein;
    private double carbohydrate;
    private double fat;

    public static FoodDto from(Food food) {
        FoodDto foodDto = new FoodDto();
        foodDto.foodName = food.getFoodName();
        foodDto.foodImgUrl = food.getFoodImgUrl();
        foodDto.calorie = food.getCalorie();
        foodDto.protein = food.getProtein();
        foodDto.carbohydrate = food.getCarbohydrate();
        foodDto.fat = food.getFat();
        return foodDto;
    }
}
