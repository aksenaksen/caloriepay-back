package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseRecordRepository;
import com.pknu.caloriepay.domain.exercise.dao.ExerciseTypeRepository;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseRecord;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.dto.RequestExerciseDto;
import com.pknu.caloriepay.domain.exercise.dto.ResponseExerciseTypeDto;
import com.pknu.caloriepay.domain.exercise.exception.ExerciseNotFoundException;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Transactional
    public void recordExercise(Long userId, String title, RequestExerciseDto requestExerciseDto){

        ExerciseType exerciseType = exerciseTypeRepository.findByName(requestExerciseDto.getExerciseName())
                .orElseThrow(() ->new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND));
//        하루 칼로리 늘려주는 기능 추가되어야함.

        exerciseRecordRepository.save(ExerciseRecord.builder()
                        .exerciseTypeId(exerciseType.getId())
                        .title(title)
                        .duration(requestExerciseDto.getDuration())
                        .userId(userId)
                        .caloriesBurned(exerciseType.calculateCalories(requestExerciseDto.getDuration()))
                        .date(LocalDate.now())
                        .build());
    }

}
