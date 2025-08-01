package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.infrastructor.ExerciseRepository;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseFinder {

    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public List<Exercise> findByDate(Long userId, LocalDate date){
        return exerciseRepository.findAllByUserIdAndDate(userId, date);
    }
}
