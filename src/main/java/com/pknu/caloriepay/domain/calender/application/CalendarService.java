package com.pknu.caloriepay.domain.calender.application;

import com.pknu.caloriepay.domain.calender.dto.out.CalendarDetailResponse;
import com.pknu.caloriepay.domain.exercise.application.ExerciseFinder;
import com.pknu.caloriepay.domain.exercise.application.ExerciseTypeFinder;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.calender.dto.out.ExerciseAndType;
import com.pknu.caloriepay.domain.meal.dao.MealRepository;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
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
public class CalendarService {

    private final MealRepository mealRepository;
    private final ExerciseFinder exerciseFinder;
    private final ExerciseTypeFinder exerciseTypeFinder;

    @Transactional(readOnly = true)
    public CalendarDetailResponse findCalendarDetail(Long userId, LocalDate date){
        return new CalendarDetailResponse(findMealRecordList(userId, date), findExerciseList(userId,date));
    }

    private List<ExerciseAndType> findExerciseList(Long userId, LocalDate date) {

        List<Exercise> exerciseRecords = exerciseFinder.findByDate(userId, date);

        return exerciseRecords.stream()
                .map(exerciseRecord -> {
                    ExerciseType type = exerciseTypeFinder.find(exerciseRecord.getId());
                    return ExerciseAndType.fromEntity(exerciseRecord, type);
                })
                .toList();
    }

    private List<MealDto> findMealRecordList(Long userId, LocalDate date){

        LocalDateTime startOfDay = date.atStartOfDay(); // 예: 2023-10-30T00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 예: 2023-10-30T23:59:59.999999999

        return mealRepository.findAllByMemberIdAndMealTimeBetween(userId,startOfDay, endOfDay).stream()
                .map(MealDto::from)
                .toList();
    }
}
