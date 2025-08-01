package com.pknu.caloriepay.domain.calender.application;

import com.pknu.caloriepay.domain.calender.application.out.CalendarDetailResponse;
import com.pknu.caloriepay.domain.exercise.application.ExerciseService;
import com.pknu.caloriepay.domain.exercise.application.out.ExerciseResponse;
import com.pknu.caloriepay.domain.meal.application.MealService;
import com.pknu.caloriepay.domain.meal.application.out.MealResponse;
import com.pknu.caloriepay.domain.tier.application.TierService;
import com.pknu.caloriepay.domain.tier.application.out.DailyTierResponse;
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

    private final MealService mealService;
    private final ExerciseService exerciseService;
    private final TierService tierService;

    @Transactional(readOnly = true)
    public CalendarDetailResponse findCalendarDetail(Long userId, LocalDate date){
        return new CalendarDetailResponse(findMealRecordList(userId, date), findExerciseList(userId,date));
    }

    @Transactional(readOnly = true)
    public List<DailyTierResponse> getCalendarByUserIdAndDate(Long userId, LocalDate start, LocalDate end){

        return tierService.findAll(userId, start, end);
    }

    private List<ExerciseResponse> findExerciseList(Long userId, LocalDate date) {

        return exerciseService.findExerciseByDate(userId,date);
    }

    private List<MealResponse> findMealRecordList(Long userId, LocalDate date){
        LocalDateTime start= date.atStartOfDay(); // 예: 2023-10-30T00:00:00
        LocalDateTime end = date.atTime(LocalTime.MAX); // 예: 2023-10-30T23:59:59.999999999

        return mealService.findAll(userId,start,end);
    }
}
