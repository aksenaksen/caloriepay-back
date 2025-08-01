//package com.pknu.caloriepay.domain.tier.domain;
//
//import com.pknu.caloriepay.global.event.MonthCalorieSummeryEvent;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.event.TransactionPhase;
//import org.springframework.transaction.event.TransactionalEventListener;
//
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//public class MonthCalorieSummeryEventListener {
//
//    private final BatchPersistTemplate batchPersistTemplate;
//
//    @Async("threadPoolTaskExecutor")
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void calculateTierAll(MonthCalorieSummeryEvent eventDto) {
//
//       batchPersistTemplate.batchPersist(() ->
//               eventDto.getDailyCalorieChangeDtoList()
//                       .stream()
//                       .map((dto) ->
//                               MonthlyTier.of(dto.getUserId(),Tier.calculateDailyTier(dto.getRemainCalorie()), LocalDate.now().minusDays(1))
//                       ).toList()
//       );
//    }
//}
