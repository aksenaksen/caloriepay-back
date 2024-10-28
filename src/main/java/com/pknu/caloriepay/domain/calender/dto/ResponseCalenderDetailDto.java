package com.pknu.caloriepay.domain.calender.dto;

import com.pknu.caloriepay.domain.exercise.dto.ResponseExerciseRecordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class ResponseCalenderDetailDto {

    private List<ResponseExerciseRecordDto> exerciseRecords;

}
