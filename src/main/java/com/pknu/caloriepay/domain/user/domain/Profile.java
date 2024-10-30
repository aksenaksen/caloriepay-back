package com.pknu.caloriepay.domain.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Builder
@Getter
@ToString
@AllArgsConstructor
public class Profile {
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer age;
    private Double height;
    private Double weight;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    private Double targetWeight;
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    protected Profile() {}

    public void updateAge(int age) {
        this.age = age;
    }
    public void updateHeight(double height) {
        this.height = height;
    }
    public void updateWeight(double weight) {
        this.weight = weight;
    }
    public void updateGoal(Goal goal) {
        this.goal = goal;
    }
    public void updateTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }
    public void updateActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

}
