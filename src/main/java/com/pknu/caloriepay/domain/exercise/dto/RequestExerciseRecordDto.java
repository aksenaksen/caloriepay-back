package com.pknu.caloriepay.domain.exercise.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestExerciseRecordDto {
    @NotBlank(message = "제목은 한글 또는 영어 1~30자를 입력해야합니다")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,30}$", message = "제목은 한글 또는 영어 1~30자를 입력해야합니다")
    private String title;

    @Valid
    private List<RequestExerciseDto> exercise;

}
