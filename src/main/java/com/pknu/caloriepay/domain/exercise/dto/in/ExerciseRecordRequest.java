package com.pknu.caloriepay.domain.exercise.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record ExerciseRecordRequest(@NotBlank(message = "제목은 한글, 영어 또는 숫자 1~30자를 입력해야 합니다.")
                                    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,30}$", message = "제목은 한글, 영어 또는 숫자 1~30자를 입력해야 합니다.")
                                    String title,

                                    @Valid
                                    @NotEmpty(message = "최소 1개 이상의 운동을 기록해야 합니다")
                                    List<ExerciseRecordCommand> exercise) {
}
