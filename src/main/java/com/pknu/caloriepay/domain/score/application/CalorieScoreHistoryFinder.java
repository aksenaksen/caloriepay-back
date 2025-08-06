package com.pknu.caloriepay.domain.score.application;

import com.pknu.caloriepay.domain.score.dao.CalorieScoreHistoryRepository;
import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CalorieScoreHistoryFinder {

    private final CalorieScoreHistoryRepository calorieScoreRepository;

    public List<CalorieScoreHistory> getCalorieScoreChangeForMonth(Long userId, Integer offset, LocalDate date) {

        return IntStream.range(0, offset)
                .mapToObj(i -> {
                    LocalDate targetDate = date.minusMonths(i);
                    return calorieScoreRepository.findLatestScoreByUserIdAndYearAndMonth(userId, targetDate.getYear(), targetDate.getMonthValue())
                            .orElse(null);
                })
                .toList();
    }

}
