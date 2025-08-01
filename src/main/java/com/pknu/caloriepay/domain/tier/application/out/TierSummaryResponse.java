package com.pknu.caloriepay.domain.tier.application.out;

import com.pknu.caloriepay.concept.Tier;

import java.util.Map;

public record TierSummaryResponse(
        Tier tier,
        long amount
) {
    public static TierSummaryResponse from(Map.Entry<Tier, Long> entry) {
        return new TierSummaryResponse(entry.getKey(), entry.getValue());
    }
}
