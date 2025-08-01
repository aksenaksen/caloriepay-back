package com.pknu.caloriepay.domain.tier.application;

import com.pknu.caloriepay.domain.tier.infrastructor.DailyTierRepository;
import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.concept.Tier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DailyTierFinder {

    private final DailyTierRepository dailyTierRepository;

    @Transactional(readOnly = true)
    public Map<Tier, Long> count(Long userId, LocalDate date){

        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        return dailyTierRepository.countByTierGroupByUserId(userId,startOfMonth,endOfMonth);
    }

    @Transactional(readOnly = true)
    public List<DailyTier> findByDate(Long userId, LocalDate start, LocalDate end) {
        return dailyTierRepository.findByUserIdAndDateBetween(userId, start, end);
    }

}
