package com.pknu.caloriepay.domain.calender.dto;

import com.pknu.caloriepay.domain.exercise.dto.ResponseExerciseRecordDto;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class ResponseCalenderDetailDto {

    private List<MealDto> mealRecords;
    private List<ResponseExerciseRecordDto> exerciseRecords;

}
