package com.pknu.caloriepay.domain.exercise.dto.in;

import com.pknu.caloriepay.domain.exercise.domain.Duration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public record ExerciseRecordCommand(
        @NotBlank(message = "운동 이름은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z가-힣\\s]{1,30}$", message = "운동 이름은 한글 또는 영어 1~30자를 입력해야 합니다")
        String exerciseName,

        @Min(value = 1, message = "운동 시간은 1분 이상이어야 합니다")
        int durationMinutes
) {
    // 도메인 객체로 변환하는 메서드
    public Duration toDuration() {
        return new Duration(durationMinutes);
    }
}
