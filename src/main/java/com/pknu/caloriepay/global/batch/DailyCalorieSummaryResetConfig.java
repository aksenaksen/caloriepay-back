package com.pknu.caloriepay.global.batch;

import com.pknu.caloriepay.domain.recommandcalorie.domain.RecommandCalorie;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DailyCalorieSummaryResetConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory emf;
    private final RedisTemplate<String, RecommandCalorie> redisTemplate;
    private final StepLoggerListener stepLoggerListener;

    @Bean
    public Step dailyCalorieSummaryResetStep(){
        return new StepBuilder("dailyCalorieSummaryResetStep", jobRepository)
                .<RecommandCalorie, RecommandCalorie>chunk(20, transactionManager)
                .reader(dailyCalorieReader())
                .processor(dailyCalorieChangeProcessor())
                .writer(dailyCalorieWriter())
                .listener(stepLoggerListener)
                .build();
    }

    @Bean
    public JpaCursorItemReader<RecommandCalorie> dailyCalorieReader(){
        return new JpaCursorItemReaderBuilder<RecommandCalorie>()
                .name("dailyCalorieReader")
                .entityManagerFactory(emf)
                .queryString("SELECT d FROM DailyCalorieChange d")
                .build();
    }

    @Bean
    public ItemProcessor<RecommandCalorie, RecommandCalorie> dailyCalorieChangeProcessor(){
        return item -> {

            String key = "userId::" + item.getUserId();
            redisTemplate.opsForValue().set(key, item);

            item.changeCalorie();
            return item;
        };
    }

    @Bean
    public JpaItemWriter<RecommandCalorie> dailyCalorieWriter(){
        return new JpaItemWriterBuilder<RecommandCalorie>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }

}
