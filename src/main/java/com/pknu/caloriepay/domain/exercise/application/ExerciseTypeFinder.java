package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseTypeRepository;
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

    @Transactional(readOnly = true)
    public List<ExerciseType> findAll(){
        return exerciseTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ExerciseType find(String name){
        return exerciseTypeRepository.findByName(name)
                .orElseThrow(() -> new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public ExerciseType find(Long id){
        return exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND));
    }
}
