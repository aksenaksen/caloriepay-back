package com.pknu.caloriepay.domain.tier.dto.out;

import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.domain.tier.domain.MonthlyTier;
import com.pknu.caloriepay.domain.tier.domain.Tier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ResponseTier {
    private Long id;

    private Long userId;

    private Tier tier;

    private LocalDate date;

    public static ResponseTier fromEntity(DailyTier tier){
        return ResponseTier.builder()
                .id(tier.getId())
                .tier(tier.getTier())
                .userId(tier.getUserId())
                .date(tier.getDate())
                .build();
    }
    public static ResponseTier fromEntity(MonthlyTier tier){
        return ResponseTier.builder()
                .id(tier.getId())
                .tier(tier.getTier())
                .userId(tier.getUserId())
                .date(tier.getDate())
                .build();
    }
}
