package com.pknu.caloriepay.domain.exercise.dto;

import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseExerciseDto {

    private Long id;
    private String type;
    private Integer kcal;

    public static ResponseExerciseDto from(ExerciseType exercise){
        return ResponseExerciseDto.builder()
                .id(exercise.getId())
                .build();
    }
}
