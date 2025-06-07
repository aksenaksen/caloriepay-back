package com.pknu.caloriepay.domain.tier.application;

import com.pknu.caloriepay.domain.tier.dao.DailyTierRepository;
import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.domain.tier.dto.out.ResponseDailyTierOfMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyTierFinder {

    private final DailyTierRepository dailyTierRepository;

//    @Transactional(readOnly = true)
//    public ResponseTier getTierByDate(Long userId, LocalDate date) {
//        return dailyTierRepository.findByUserIdAndDate(userId, date)
//                .map(ResponseTier::fromEntity)
//                .orElse(null);
//    }

    @Transactional(readOnly = true)
    public List<ResponseDailyTierOfMonth> getDailyTierOfMonth(Long userId, LocalDate date){
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        return dailyTierRepository.countByTierGroupByUserId(userId,startOfMonth,endOfMonth);
    }

    @Transactional(readOnly = true)
    public List<DailyTier> findByDate(Long userId, LocalDate start, LocalDate end) {
        return dailyTierRepository.findByUserIdAndDateBetween(userId, start, end);
    }

}
