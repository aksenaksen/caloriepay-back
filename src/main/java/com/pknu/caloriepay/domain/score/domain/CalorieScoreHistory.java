package com.pknu.caloriepay.domain.score.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalorieScoreHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate date;

    private Integer score;

    public static CalorieScoreHistory createCalorieScoreOld(long userId, double remainCalorie){
        CalorieScoreHistory newScore = CalorieScoreHistory.builder()
                .userId(userId)
                .date(LocalDate.now())
                .build();
        newScore.calculateScore(remainCalorie);
        return newScore;
    }

    public void calculateScore(double remainCalorie){
        this.score= (int) (remainCalorie/2);
    }
}
