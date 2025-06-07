package com.pknu.caloriepay.domain.calender.dto.out;

import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.domain.tier.domain.Tier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CalendarResponse {

    private Long id;
    private Long userId;
    private Tier tier;
    private LocalDate date;

    public static CalendarResponse fromEntity(DailyTier tier){
        return CalendarResponse.builder()
                .id(tier.getId())
                .tier(tier.getTier())
                .userId(tier.getUserId())
                .date(tier.getDate())
                .build();
    }
}
