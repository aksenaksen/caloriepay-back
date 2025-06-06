package com.pknu.caloriepay.domain.score.domain;

import com.pknu.caloriepay.domain.user.domain.ActivityLevel;
import com.pknu.caloriepay.domain.user.domain.Gender;
import com.pknu.caloriepay.domain.user.domain.Goal;
import com.pknu.caloriepay.domain.user.domain.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyCalorieChange {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private double dailyRecommendedCalorie;

    private double remainCalorie;

    private double totalCalorie = 0;

    public void calculateTotalCalorie(){
        this.totalCalorie+=this.remainCalorie;
    }
    public void plusCalorie(double amount){
        this.remainCalorie +=amount;
    }
    public void minusCalorie(double amount) {this.remainCalorie -=amount;}

    public void resetTotalCalorie(){ this.totalCalorie =0;}
    public void resetCalorie(){
        this.remainCalorie = dailyRecommendedCalorie;
    }

    public void changeCalorie(){
        calculateTotalCalorie();
        resetCalorie();
    }



    public void resetRecommendedCalorie(Profile profile) {
        double bmr = calculateBMR(profile);
        double recommendedCalories = adjustForActivityLevel(bmr, profile.getActivityLevel());

        // 목표에 따라 칼로리 조정
        if (profile.getGoal() == Goal.DIET) {
            recommendedCalories *= 0.8;
        }

        this.dailyRecommendedCalorie = recommendedCalories;
    }

    // BMR 계산
    private double calculateBMR(Profile profile) {
        if (profile.getGender() == Gender.MALE) {
            return 88.362 + (13.397 * profile.getWeight()) + (4.799 * profile.getHeight()) - (5.677 * profile.getAge());
        } else {
            return 447.593 + (9.247 * profile.getWeight()) + (3.098 * profile.getHeight()) - (4.330 * profile.getAge());
        }
    }

    // 활동 수준에 따른 칼로리 조정
    private double adjustForActivityLevel(double bmr, ActivityLevel activityLevel) {
        switch (activityLevel) {
            case NONE:
                return bmr * 1.2;
            case LOW:
                return bmr * 1.375;
            case HIGH:
                return bmr * 1.725;
            default:
                return bmr;
        }
    }
}
