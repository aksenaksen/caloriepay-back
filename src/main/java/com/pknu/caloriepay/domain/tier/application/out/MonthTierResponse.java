package com.pknu.caloriepay.domain.tier.application.out;

import com.pknu.caloriepay.domain.tier.domain.MonthlyTier;
import com.pknu.caloriepay.concept.Tier;

import java.time.LocalDate;

public record MonthTierResponse(
        long id,
        long userId,
        Tier tier,
        LocalDate date
) {

    public static MonthTierResponse from(MonthlyTier monthlyTier) {
        return new MonthTierResponse(
                monthlyTier.getId(),
                monthlyTier.getUserId(),
                monthlyTier.getTier(),
                monthlyTier.getDate()
        );
    }
}