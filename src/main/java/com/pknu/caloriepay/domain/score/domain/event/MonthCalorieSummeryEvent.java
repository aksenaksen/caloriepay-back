package com.pknu.caloriepay.domain.score.domain.event;

import com.pknu.caloriepay.domain.score.dto.out.ResponseDailyCalorieChangeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MonthCalorieSummeryEvent {
    private List<ResponseDailyCalorieChangeDto> dailyCalorieChangeDtoList;
}
