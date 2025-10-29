package com.pknu.caloriepay.global.batch;

import com.pknu.caloriepay.domain.user.dao.MemberRankingRedisRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
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

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DailyUserScoreConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory emf;
    private final StepLoggerListener stepLoggerListener;
    private final MemberRankingRedisRepository memberRankingRedisRepository;


    @Bean
    public Step dailyUserScoreStep(){

        return new StepBuilder("DailyUserScoreStep", jobRepository)
                .<Object[], Member>chunk(20, transactionManager)
                .reader(dailyCalorieSummaryScoreReader())
                .processor(dailyCalorieSummaryScoreProcessor())
                .writer(dailyCalorieSummaryScoreWriter())
                .listener(stepLoggerListener)
                .build();
    }

    @Bean
    public JpaCursorItemReader<Object[]> dailyCalorieSummaryScoreReader(){
        return new JpaCursorItemReaderBuilder<Object[]>()
                .name("dailyCalorieSummaryScoreReader")
                .entityManagerFactory(emf)
                .queryString("SELECT m, c.score FROM Member m " +
                           "JOIN CalorieScoreHistory c ON m.id = c.userId " +
                           "WHERE c.date = :currentDate")
                .parameterValues(java.util.Map.of("currentDate", LocalDate.now()))
                .build();
    }

    @Bean
    public ItemProcessor<Object[], Member> dailyCalorieSummaryScoreProcessor(){
        return item -> {
            Member member = (Member) item[0];
            Integer todayScore = (Integer) item[1];

            member.updateScore(todayScore);
            memberRankingRedisRepository.add(member.getId(), member.getScore());
            log.info("Member ID: {}, Updated Score: {}", member.getId(), todayScore);
            
            return member;
        };
    }

    @Bean
    public JpaItemWriter<Member> dailyCalorieSummaryScoreWriter(){

        return new JpaItemWriterBuilder<Member>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }
}
