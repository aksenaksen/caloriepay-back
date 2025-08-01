package com.pknu.caloriepay.domain.exercise.application.command;

import com.pknu.caloriepay.domain.exercise.domain.Duration;

public record ExerciseRecordCommand(
        String title,
        long userId,
        String exerciseName,
        int duration
) {
    // 도메인 객체로 변환하는 메서드
    public Duration toDuration() {
        return new Duration(duration);
    }

}
