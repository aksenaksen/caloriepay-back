package com.pknu.caloriepay.domain.calender.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseRecordRepository;
import com.pknu.caloriepay.domain.exercise.dao.ExerciseTypeRepository;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseRecord;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.dto.out.ResponseExerciseRecordDto;
import com.pknu.caloriepay.domain.exercise.exception.ExerciseNotFoundException;
import com.pknu.caloriepay.domain.meal.dao.MealRepository;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarDetailSearchService {

    private final ExerciseRecordRepository exerciseRecordRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final MealRepository mealRepository;

    @Transactional(readOnly = true)
    public List<ResponseExerciseRecordDto> getExerciseRecordList(Long userId, LocalDate date) {
        // 1. 해당 날짜의 운동 기록을 조회
        List<ExerciseRecord> exerciseRecords = exerciseRecordRepository.findAllByUserIdAndDate(userId, date);

        return exerciseRecords.stream()
                .map(exerciseRecord -> {
                    ExerciseType type = exerciseTypeRepository.findById(exerciseRecord.getExerciseTypeId())
                            .orElseThrow(() -> new ExerciseNotFoundException(ResCode.EXERCISE_NOT_FOUND)); // 예외 처리

                    return ResponseExerciseRecordDto.fromEntity(exerciseRecord, type);
                })
                .toList();
    }
    @Transactional(readOnly = true)
    public List<MealDto> getMealRecordList(Long userId, LocalDate date){

        LocalDateTime startOfDay = date.atStartOfDay(); // 예: 2023-10-30T00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 예: 2023-10-30T23:59:59.999999999


        return mealRepository.findAllByMemberIdAndMealTimeBetween(userId,startOfDay, endOfDay).stream()
                .map(MealDto::from)
                .toList();
    }
}
