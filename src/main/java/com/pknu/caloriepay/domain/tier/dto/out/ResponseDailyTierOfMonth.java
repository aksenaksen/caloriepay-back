package com.pknu.caloriepay.domain.tier.dto.out;

import com.pknu.caloriepay.domain.tier.domain.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDailyTierOfMonth {
    Tier tier;

    Long amount;


}
