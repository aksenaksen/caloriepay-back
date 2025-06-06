package com.pknu.caloriepay.domain.tier.application;

import com.pknu.caloriepay.domain.tier.dao.DailyTierRepository;
import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import com.pknu.caloriepay.domain.tier.domain.Tier;
import com.pknu.caloriepay.domain.tier.dto.out.ResponseDailyTierOfMonth;
import com.pknu.caloriepay.domain.tier.dto.out.ResponseTier;
import com.pknu.caloriepay.global.event.DailyCalorieSummaryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DailyTierService{

    private final DailyTierRepository dailyTierRepository;

    @Transactional(readOnly = true)
    public ResponseTier getTierByDate(Long userId, LocalDate date) {
        return dailyTierRepository.findByUserIdAndDate(userId, date)
            .map(ResponseTier::fromEntity)
            .orElse(null);
        }


    public List<ResponseDailyTierOfMonth> getDailyTierOfMonth(Long userId, LocalDate date){
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        return dailyTierRepository.countByTierGroupByUserId(userId,startOfMonth,endOfMonth);
    }


}
