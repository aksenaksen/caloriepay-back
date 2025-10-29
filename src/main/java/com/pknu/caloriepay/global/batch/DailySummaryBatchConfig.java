package com.pknu.caloriepay.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DailySummaryBatchConfig {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;

    private final Step dailyCalorieSummaryResetStep;
    private final Step dailyCalorieSummaryScoreStep;
    private final Step dailyCalorieSummaryTierStep;
    private final Step dailyUserScoreStep;

    @Bean
    public Job dailySummaryJob(){
        return new JobBuilder("dailySummaryJob", jobRepository)
                .start(dailyCalorieSummaryScoreStep)
                .next(dailyCalorieSummaryTierStep)
                .next(dailyCalorieSummaryResetStep)
                .next(dailyUserScoreStep)
                .build();
    }

    @Scheduled(cron = "0 1 0 * * *", zone = "Asia/Seoul")
    public void runDailySummaryJob(){
        try{
            JobParameters params = new JobParametersBuilder()
                    .addString("runTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(dailySummaryJob(),params);
            log.info("✅ Batch Job {} Completed. Status: {}", jobExecution.getJobInstance().getJobName(),jobExecution.getStatus());
        } catch (Exception e){
            log.error("❌ Batch Job Failed",e);
        }
    }



}

