package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.infrastructor.ExerciseTypeRepository;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.exception.ExerciseNotFoundException;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseTypeFinder {

    private final ExerciseTypeRepository exerciseTypeRepository;

    public List<ExerciseType> findAll(){
        return exerciseTypeRepository.findAll();
    }

    public ExerciseType find(String name){
        return exerciseTypeRepository.findByName(name)
                .orElseThrow(() -> new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND));
    }

    public ExerciseType find(Long id){
        return exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND));
    }
}
