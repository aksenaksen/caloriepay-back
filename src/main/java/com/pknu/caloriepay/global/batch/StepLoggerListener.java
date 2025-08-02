package com.pknu.caloriepay.global.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StepLoggerListener {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("✅ Step [{}] Start.. ID : {}" ,stepExecution.getStepName(),stepExecution.getId());
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        long readCnt = stepExecution.getReadCount();
        long writeCnt = stepExecution.getWriteCount();
        long skipCnt = stepExecution.getSkipCount();
        log.info("✅ Step {} Finished - Read Item : {}, Skipped Item : {} , Write Item : {}"
                ,stepExecution.getStepName(), readCnt, skipCnt, writeCnt);

    }
}
