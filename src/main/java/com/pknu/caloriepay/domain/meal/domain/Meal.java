package com.pknu.caloriepay.domain.meal.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime mealTime;
    private int totalCalorie;
    private String mealImgUrl;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();

    @Builder
    public Meal(LocalDateTime mealTime, int totalCalorie, String mealImgUrl) {
        this.mealTime = mealTime;
        this.totalCalorie = totalCalorie;
        this.mealImgUrl = mealImgUrl;
    }

    // 연관관계 편의 메서드
    public void addFood(Food food) {
        foods.add(food);
        food.setMeal(this);
        this.totalCalorie += food.getCalorie();  // 각 Food 칼로리를 Meal 총 칼로리에 추가
    }

    public void addFoods(List<Food> foodList) {
        for (Food food : foodList) {
            addFood(food);
        }
    }

}
