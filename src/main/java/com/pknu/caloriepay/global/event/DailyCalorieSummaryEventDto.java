package com.pknu.caloriepay.global.event;

import com.pknu.caloriepay.domain.score.dto.ResponseDailyCalorieChangeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DailyCalorieSummaryEventDto {
    private List<ResponseDailyCalorieChangeDto> dailyCalorieChangeDtoList;
}
