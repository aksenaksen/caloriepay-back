package com.pknu.caloriepay.domain.score.application;

import com.pknu.caloriepay.domain.score.dao.DailyCalorieChangeRepository;
import com.pknu.caloriepay.domain.score.domain.DailyCalorieChange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyCalorieChangeFinder {


    private final DailyCalorieChangeRepository dailyCalorieChangeRepository;

    public DailyCalorieChange find(Long userId) {
        return dailyCalorieChangeRepository.findByUserId(userId)
                .orElseThrow(); // 값이 없을 경우 null을 반환
    }


}
