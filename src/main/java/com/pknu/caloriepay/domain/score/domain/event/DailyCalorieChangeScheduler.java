package com.pknu.caloriepay.domain.score.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyCalorieChangeScheduler {

    private final DailyCalorieChangeProcessor dailyCalorieChangeProcessor;

//  일별 칼로리 정산
    @Async("threadPoolTaskExecutor")
    @Scheduled(cron = "0 1 0 * * *", zone = "Asia/Seoul")
    public void dailyCalorieSummary() {
        dailyCalorieChangeProcessor.processDailyCalorieChange();
    }

//  월별칼로리정산
    @Async("threadPoolTaskExecutor")
    @Scheduled(cron = "0 5 0 1 * *" ,zone = "Asia/Seoul")
    public void totalCalorieSummary() {
        dailyCalorieChangeProcessor.processMonthlyCalorieChange();
    }


}
