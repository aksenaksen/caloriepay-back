package com.pknu.caloriepay.domain.calender.dto.out;


import com.pknu.caloriepay.domain.exercise.domain.Duration;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ExerciseAndType {


    private Long id;

    private Long userId;

    private String title;

    private String exerciseTypeName;

    private Duration duration;

    private double caloriesBurned;

    private LocalDate date;

    public static ExerciseAndType fromEntity(Exercise exerciseRecord, ExerciseType type){
        return ExerciseAndType.builder()
                .id(exerciseRecord.getId())
                .userId(exerciseRecord.getUserId())
                .title(exerciseRecord.getTitle())
                .exerciseTypeName(type.getName())
                .duration(exerciseRecord.getDuration())
                .caloriesBurned(exerciseRecord.getCaloriesBurned())
                .date(exerciseRecord.getDate())
                .build();
    }
}
