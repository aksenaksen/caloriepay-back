package com.pknu.caloriepay.domain.exercise.dto;


import com.pknu.caloriepay.domain.exercise.domain.ExerciseRecord;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ResponseExerciseRecordDto {


    private Long id;

    private Long userId;

    private String title;

    private String exerciseTypeName;

    private Integer duration;

    private double caloriesBurned;

    private LocalDate date;

    public static ResponseExerciseRecordDto fromEntity(ExerciseRecord exerciseRecord, ExerciseType type){
        return ResponseExerciseRecordDto.builder()
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
