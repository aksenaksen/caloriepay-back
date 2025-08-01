package com.pknu.caloriepay.domain.tier.application;

import com.pknu.caloriepay.domain.tier.infrastructor.MonthlyTierRepository;
import com.pknu.caloriepay.domain.tier.domain.MonthlyTier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MonthlyTierFinder {

    private final MonthlyTierRepository monthlyTierRepository;

    public MonthlyTier find(Long userId, LocalDate date) {
        return monthlyTierRepository.findByUserIdAndDate(userId, date)
                .orElseThrow();
    }

}
