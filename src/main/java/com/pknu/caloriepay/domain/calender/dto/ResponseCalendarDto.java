package com.pknu.caloriepay.domain.calender.dto;

import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.domain.tier.domain.Tier;
import com.pknu.caloriepay.domain.tier.dto.ResponseTier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ResponseCalendarDto {

    private Long id;
    private Long userId;
    private Tier tier;
    private LocalDate date;

    public static ResponseCalendarDto fromEntity(DailyTier tier){
        return ResponseCalendarDto.builder()
                .id(tier.getId())
                .tier(tier.getTier())
                .userId(tier.getUserId())
                .date(tier.getDate())
                .build();
    }
}
