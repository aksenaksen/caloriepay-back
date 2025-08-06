package com.pknu.caloriepay.domain.recommandcalorie.application;

import com.pknu.caloriepay.domain.recommandcalorie.domain.RecommandCalorie;
import com.pknu.caloriepay.domain.recommandcalorie.infrastructor.RecommandCalorieHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommandCalorieFinder {


    private final RecommandCalorieHistoryRepository dailyCalorieChangeRepository;

    public RecommandCalorie find(Long userId) {
        return dailyCalorieChangeRepository.findByUserId(userId)
                .orElseThrow(); // 값이 없을 경우 null을 반환
    }


}
