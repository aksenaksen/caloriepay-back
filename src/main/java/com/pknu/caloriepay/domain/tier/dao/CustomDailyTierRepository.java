package com.pknu.caloriepay.domain.tier.dao;

import com.pknu.caloriepay.domain.tier.dto.out.ResponseDailyTierOfMonth;

import java.time.LocalDate;
import java.util.List;

public interface CustomDailyTierRepository {
    public List<ResponseDailyTierOfMonth> countByTierGroupByUserId(Long userId, LocalDate start, LocalDate end);
}
