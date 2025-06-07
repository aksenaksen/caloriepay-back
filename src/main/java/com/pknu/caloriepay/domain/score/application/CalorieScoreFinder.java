package com.pknu.caloriepay.domain.score.application;

import com.pknu.caloriepay.domain.score.dao.DailyCalorieChangeRepository;
import com.pknu.caloriepay.domain.score.dto.out.ResponseDailyCalorieChangeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalorieScoreFinder {

    private final DailyCalorieChangeRepository dailyCalorieChangeRepository;

    public ResponseDailyCalorieChangeDto find(Long userId) {
        return dailyCalorieChangeRepository.findByUserId(userId)
                .map(ResponseDailyCalorieChangeDto::fromEntity)
                .orElse(null); // 값이 없을 경우 null을 반환
    }


}
