package com.pknu.caloriepay.domain.exercise.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExerciseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double averageCaloriesPerMinute;

    public double calculateCalories(Integer time){
        return time * this.averageCaloriesPerMinute;
    }
}
