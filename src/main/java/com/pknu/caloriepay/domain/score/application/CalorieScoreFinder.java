package com.pknu.caloriepay.domain.score.application;

import com.pknu.caloriepay.domain.score.dao.CalorieScoreRepository;
import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CalorieScoreFinder {

    private final CalorieScoreRepository calorieScoreRepository;

    public List<CalorieScore> getCalorieScoreChangeForMonth(Long userId, Integer offset, LocalDate date) {

        return IntStream.range(0, offset)
                .mapToObj(i -> {
                    LocalDate targetDate = date.minusMonths(i);
                    return calorieScoreRepository.findLatestScoreByUserIdAndYearAndMonth(userId, targetDate.getYear(), targetDate.getMonthValue())
                            .orElse(null);
                })
                .toList();
    }

}
