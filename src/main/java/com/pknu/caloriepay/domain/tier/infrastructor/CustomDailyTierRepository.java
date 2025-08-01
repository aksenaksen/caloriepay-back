package com.pknu.caloriepay.domain.tier.infrastructor;

import com.pknu.caloriepay.concept.Tier;

import java.time.LocalDate;
import java.util.Map;

public interface CustomDailyTierRepository {
    Map<Tier, Long> countByTierGroupByUserId(Long userId, LocalDate start, LocalDate end);
}
