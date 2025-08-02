package com.pknu.caloriepay.domain.score.event;

import com.pknu.caloriepay.domain.score.dao.DailyCalorieChangeRepository;
import com.pknu.caloriepay.domain.score.domain.DailyCalorieChange;
import com.pknu.caloriepay.domain.score.application.out.ResponseDailyCalorieChangeDto;
import com.pknu.caloriepay.global.util.BatchPersistTemplate;
import com.pknu.caloriepay.global.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class processor {

    private final DailyCalorieChangeRepository dailyCalorieChangeRepository;
    private final BatchPersistTemplate batchPersistTemplate;

    @Transactional
    public void processDailyCalorieChange(){
        List<DailyCalorieChange> entityList = dailyCalorieChangeRepository.findAll();

        DailyCalorieSummaryEvent resultDto=new DailyCalorieSummaryEvent(entityList
                .stream()
                .map(ResponseDailyCalorieChangeDto::fromEntity)
                .toList());

        entityList.forEach(DailyCalorieChange::changeCalorie);
        batchPersistTemplate.batchPersist(() -> entityList);
        Events.publish(resultDto);
    }

    @Transactional
    public void processMonthlyCalorieChange(){
        List<DailyCalorieChange> entityList = dailyCalorieChangeRepository.findAll();
        MonthCalorieSummeryEvent resultDto = new MonthCalorieSummeryEvent(entityList
                .stream()
                .map(ResponseDailyCalorieChangeDto::fromEntity)
                .toList());

        entityList.forEach(DailyCalorieChange::resetCalorie);
        batchPersistTemplate.batchPersist(() -> entityList);
        Events.publish(resultDto);
    }
}
