package com.pknu.caloriepay.domain.tier.application.out;

import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.concept.Tier;

import java.time.LocalDate;

public record DailyTierResponse(
        long id,
        long userId,
        Tier tier,
        LocalDate date
) {

    public static DailyTierResponse from(DailyTier dailyTier) {

        return new DailyTierResponse(
                dailyTier.getId(),
                dailyTier.getUserId(),
                dailyTier.getTier(),
                dailyTier.getDate()
        );
    }
}
