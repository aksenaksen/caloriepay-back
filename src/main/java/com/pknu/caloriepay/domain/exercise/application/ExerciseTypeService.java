package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseTypeRepository;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.dto.out.ResponseExerciseTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Transactional(readOnly = true)
    public List<ResponseExerciseTypeDto> getExerciseList(){
        List<ExerciseType> exerciseType = exerciseTypeRepository.findAll();
        return exerciseType.stream().map(ResponseExerciseTypeDto::fromEntity)
                .toList();
    }
}
