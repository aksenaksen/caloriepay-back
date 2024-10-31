package com.pknu.caloriepay.domain.exercise.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestExerciseRecordDto {
    @NotBlank(message = "제목은 한글, 영어 또는 숫자 1~30자를 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,30}$", message = "제목은 한글, 영어 또는 숫자 1~30자를 입력해야 합니다.")
    private String title;

    @Valid
    private List<RequestExerciseDto> exercise;

}
