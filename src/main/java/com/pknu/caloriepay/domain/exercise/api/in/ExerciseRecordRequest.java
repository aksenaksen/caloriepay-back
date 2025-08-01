package com.pknu.caloriepay.domain.exercise.api.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record ExerciseRecordRequest(@NotBlank(message = "제목은 한글, 영어 또는 숫자 1~30자를 입력해야 합니다.")
                                    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,30}$", message = "제목은 한글, 영어 또는 숫자 1~30자를 입력해야 합니다.")
                                    String title,

                                    @Valid
                                    @NotEmpty(message = "최소 1개 이상의 운동을 기록해야 합니다")
                                    List<ExerciseRequest> exercises) {


    public record ExerciseRequest( @NotBlank(message = "운동 이름은 필수입니다")
                                   @Pattern(regexp = "^[a-zA-Z가-힣\\s]{1,30}$", message = "운동 이름은 한글 또는 영어 1~30자를 입력해야 합니다")
                                   String exerciseName,

                                   @Min(value = 1, message = "운동 시간은 1분 이상이어야 합니다")
                                   int durationMinutes){

    }
}
