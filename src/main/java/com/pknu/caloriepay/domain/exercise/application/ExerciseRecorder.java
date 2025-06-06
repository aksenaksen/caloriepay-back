package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseRepository;
import com.pknu.caloriepay.domain.exercise.domain.Duration;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExerciseRecorder {

    private final ExerciseRepository exerciseRecordRepository;

    @Transactional
    public void record(Long userId, String title, ExerciseType exerciseType, Duration duration) {
        Exercise exercise = Exercise.of(userId,exerciseType,title,duration);
        exerciseRecordRepository.save(exercise);
        exercise.recordExercise();
    }
}
