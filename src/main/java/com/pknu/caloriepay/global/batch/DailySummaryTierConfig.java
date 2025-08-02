package com.pknu.caloriepay.global.batch;

import com.pknu.caloriepay.concept.Tier;
import com.pknu.caloriepay.domain.score.domain.DailyCalorieChange;
import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DailySummaryTierConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JpaCursorItemReader<DailyCalorieChange> dailyCalorieReader;
    private final StepLoggerListener stepLoggerListener;
    private final EntityManagerFactory emf;

    @Bean
    public Step dailyCalorieSummaryTierStep(){
        return new StepBuilder("dailyCalorieSummaryTierStep", jobRepository)
                .<DailyCalorieChange, DailyTier>chunk(20, transactionManager)
                .reader(dailyCalorieReader)
                .processor(dailyCalorieSummaryTierProcessor())
                .writer(dailyCalorieSummaryTierWriter())
                .listener(stepLoggerListener)
                .build();
    }

    @Bean
    public ItemProcessor<DailyCalorieChange, DailyTier>  dailyCalorieSummaryTierProcessor(){
        return item -> DailyTier.of(item.getUserId(),
                Tier.calculateDailyTier(item.getRemainCalorie()),
                LocalDate.now().minusDays(1));
    }

    @Bean
    public JpaItemWriter<DailyTier> dailyCalorieSummaryTierWriter(){
        return new JpaItemWriterBuilder<DailyTier>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
    }
}
