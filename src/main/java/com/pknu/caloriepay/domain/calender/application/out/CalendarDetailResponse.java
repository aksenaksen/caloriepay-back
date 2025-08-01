package com.pknu.caloriepay.domain.calender.application.out;

import com.pknu.caloriepay.domain.exercise.application.out.ExerciseResponse;
import com.pknu.caloriepay.domain.meal.application.out.MealResponse;

import java.util.List;

public record CalendarDetailResponse(
        List<MealResponse> mealRecords,
        List<ExerciseResponse> exerciseRecords
) {
}
