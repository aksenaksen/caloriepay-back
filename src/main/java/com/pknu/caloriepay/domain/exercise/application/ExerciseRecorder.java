package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.concept.ExerciseDetail;
import com.pknu.caloriepay.domain.exercise.infrastructor.ExerciseRepository;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExerciseRecorder {

    private final ExerciseRepository exerciseRecordRepository;

    @Transactional
    public void record(Long userId, String title, ExerciseDetail exerciseDetail) {

        Exercise exercise = Exercise.recordExercise(userId,
                title,
                exerciseDetail.exerciseTypeId(),
                exerciseDetail.burnedCalorie(),
                exerciseDetail.duration());

        exerciseRecordRepository.save(exercise);
        exercise.recordExercise();
    }
}
