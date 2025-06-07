package com.pknu.caloriepay.domain.tier.domain;

import com.pknu.caloriepay.global.event.DailyCalorieSummaryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DailyCalorieSummaryEventListener {

    private final BatchPersistTemplate batchPersistTemplate;

    @Async("threadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void calculateTierAll(DailyCalorieSummaryEvent eventDto) {
//      요일 정산시 어제날짜로 티어가 정산됨.
//        List<DailyTier> tierList = eventDto.getDailyCalorieChangeDtoList().forEach(calorieChange -> {
//            Long userId = calorieChange.getUserId();
//
////            dailyTierRepository.save(DailyTier.builder()
////                    .userId(userId)
////                    .tier(Tier.calculateDailyTier(calorieChange.getRemainCalorie())) // 티어 계산
////                    .date(LocalDate.now().minusDays(1)) // 어제 날짜
////                    .build()); // DailyTier 저장
//        });

        batchPersistTemplate.batchPersist(() ->
            eventDto.getDailyCalorieChangeDtoList()
                    .stream()
                    .map((dto) ->
                            DailyTier.of(dto.getUserId(),Tier.calculateDailyTier(dto.getRemainCalorie()), LocalDate.now().minusDays(1))
                    ).toList()
        );

    }
}
