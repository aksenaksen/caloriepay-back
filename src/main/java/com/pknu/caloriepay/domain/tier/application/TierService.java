package com.pknu.caloriepay.domain.tier.application;

import com.pknu.caloriepay.domain.tier.application.out.DailyTierResponse;
import com.pknu.caloriepay.domain.tier.application.out.MonthTierResponse;
import com.pknu.caloriepay.domain.tier.application.out.TierSummaryResponse;
import com.pknu.caloriepay.domain.tier.domain.MonthlyTier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TierService {

    private final DailyTierFinder dailyTierFinder;
    private final MonthlyTierFinder monthlyTierFinder;

    @Transactional(readOnly = true)
    public List<DailyTierResponse> findAll(Long userId, LocalDate start, LocalDate end) {
        return dailyTierFinder.findByDate(userId,start,end)
                .stream()
                .map(DailyTierResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MonthTierResponse findMonthTier(Long userId, LocalDate date) {
        MonthlyTier tier = monthlyTierFinder.find(userId , date);
        return MonthTierResponse.from(tier);
    }

    public List<TierSummaryResponse> findSummary(Long userId, LocalDate cur) {

        return dailyTierFinder.count(userId, cur)
                .entrySet().stream()
                .map(TierSummaryResponse::from)
                .toList();
    }

}
