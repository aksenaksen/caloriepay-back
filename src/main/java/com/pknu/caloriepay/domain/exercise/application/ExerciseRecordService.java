package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseRecordRepository;
import com.pknu.caloriepay.domain.exercise.dao.ExerciseTypeRepository;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseRecord;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.dto.RequestExerciseDto;
import com.pknu.caloriepay.domain.exercise.dto.ResponseExerciseTypeDto;
import com.pknu.caloriepay.domain.exercise.exception.ExerciseNotFoundException;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.event.ExerciseEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;
    @Transactional
    public void recordExercise(Long userId, String title, List<RequestExerciseDto> requestExerciseDtoList) {

        requestExerciseDtoList.forEach(requestExerciseDto -> {
            ExerciseType exerciseType = exerciseTypeRepository.findByName(requestExerciseDto.getExerciseName())
                    .orElseThrow(() -> new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND));
            double caloriesBurned = exerciseType.calculateCalories(requestExerciseDto.getDuration());

            exerciseRecordRepository.save(
                    ExerciseRecord.builder()
                            .exerciseTypeId(exerciseType.getId())
                            .title(title)
                            .duration(requestExerciseDto.getDuration())
                            .userId(userId)
                            .caloriesBurned(caloriesBurned)
                            .date(LocalDate.now())
                            .build()
            );

            applicationEventPublisher.publishEvent(new ExerciseEventDto(userId, caloriesBurned));
        });
    }

}
