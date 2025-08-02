package com.pknu.caloriepay.domain.score.event;

import com.pknu.caloriepay.domain.score.application.out.ResponseDailyCalorieChangeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DailyCalorieSummaryEvent {

    private List<ResponseDailyCalorieChangeDto> dailyCalorieChangeDtoList;

}
