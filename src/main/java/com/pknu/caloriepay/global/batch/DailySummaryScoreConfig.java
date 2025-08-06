package com.pknu.caloriepay.global.batch;

import com.pknu.caloriepay.domain.recommandcalorie.domain.RecommandCalorie;
import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DailySummaryScoreConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory emf;
    private final JpaCursorItemReader<RecommandCalorie> dailyCalorieReader;
    private final StepLoggerListener stepLoggerListener;


    @Bean
    public Step dailyCalorieSummaryScoreStep(){
        return new StepBuilder("dailyCalorieSummaryScoreStep", jobRepository)
                .<RecommandCalorie, CalorieScoreHistory>chunk(20, transactionManager)
                .reader(dailyCalorieReader)
                .processor(dailyCalorieSummaryScoreProcessor())
                .writer(dailyCalorieSummaryScoreWriter())
                .listener(stepLoggerListener)
                .build();
    }

    @Bean
    public JpaCursorItemReader<CalorieScoreHistory> dailyCalorieSummaryScoreReader(){
        return new JpaCursorItemReaderBuilder<CalorieScoreHistory>()
                .name("dailyCalorieSummaryScoreReader")
                .entityManagerFactory(emf)
                .queryString("SELECT c FROM CalorieScore c")
                .build();
    }

    @Bean
    public ItemProcessor<RecommandCalorie, CalorieScoreHistory> dailyCalorieSummaryScoreProcessor(){
        return item -> {
//                log.info("Before item={}", item.toString());
//                String key = "userId::" + item.getUserId();
//
//                DailyCalorieChange dailyCalorieChange = redisTemplate.opsForValue().get(key);
//                if(dailyCalorieChange == null) return null;

            return CalorieScoreHistory.createCalorieScoreOld(item.getUserId(),item.getRemainCalorie());
        };
    }

    @Bean
    public JpaItemWriter<CalorieScoreHistory> dailyCalorieSummaryScoreWriter(){

        return new JpaItemWriterBuilder<CalorieScoreHistory>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
    }
}
