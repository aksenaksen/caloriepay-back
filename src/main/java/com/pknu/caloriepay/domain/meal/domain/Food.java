package com.pknu.caloriepay.domain.meal.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "food")
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private String foodName;
    private String foodImgUrl;
    private int calorie;
    private double protein;
    private double carbohydrate;
    private double fat;

    @ManyToOne
    @JoinColumn
    private Meal meal;

    @Builder
    public Food(String foodName, String foodImgUrl, int calorie, double protein, double carbohydrate, double fat) {
        this.foodName = foodName;
        this.foodImgUrl = foodImgUrl;
        this.calorie = calorie;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
    }

    // Meal 연관관계 설정
    protected void setMeal(Meal meal) {
        this.meal = meal;
    }


}
