package com.pknu.caloriepay.domain.tier.application;

import com.pknu.caloriepay.domain.tier.dao.MonthlyTierRepository;
import com.pknu.caloriepay.domain.tier.dto.out.ResponseTier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MonthlyTierFinder {

    private final MonthlyTierRepository monthlyTierRepository;

    public ResponseTier getTierByDate(Long userId, LocalDate date) {
        return monthlyTierRepository.findByUserIdAndDate(userId, date)
                .map(ResponseTier::fromEntity)
                .orElse(null);
    }

}
