package com.pknu.caloriepay.domain.exercise.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RequestExerciseDto {

    @NotBlank(message = "운동이름은 한글 또는 영어 1~30자를 입력해야합니다")
    @Pattern(regexp = "^[a-zA-Z가-힣\\s]{1,30}$", message = "운동이름은 한글 또는 영어 1~30자를 입력해야합니다")
    private String exerciseName;

    @Min(value = 1, message = "운동시간은 1 이상이어야합니다.")
    private Integer duration;
}
