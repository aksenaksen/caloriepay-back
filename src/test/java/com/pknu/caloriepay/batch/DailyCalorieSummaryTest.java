package com.pknu.caloriepay.batch;

import com.pknu.caloriepay.domain.recommandcalorie.domain.RecommandCalorie;
import com.pknu.caloriepay.domain.score.dao.CalorieScoreHistoryRepository;
import com.pknu.caloriepay.domain.recommandcalorie.infrastructor.RecommandCalorieHistoryRepository;
import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import com.pknu.caloriepay.domain.tier.infrastructor.DailyTierRepository;
import com.pknu.caloriepay.domain.user.domain.ActivityLevel;
import com.pknu.caloriepay.domain.user.domain.Gender;
import com.pknu.caloriepay.domain.user.domain.Goal;
import com.pknu.caloriepay.domain.user.domain.Profile;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
public class DailyCalorieSummaryTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job dailyCalorieSummaryScoreJob;

    @Autowired
    private DailyTierRepository dailyTierRepository;

    @Autowired
    private RecommandCalorieHistoryRepository repository;

    @Autowired
    private CalorieScoreHistoryRepository calorieScoreRepository;

    @Autowired
    private RedisTemplate<String, RecommandCalorie> redisTemplate;

    @PostConstruct
    public void init(){
        List<RecommandCalorie> dailyCalorieChanges = new ArrayList<>();
        List<CalorieScoreHistory> calorieScores = new ArrayList<>();
        Random random = new Random();
        LocalDate today = LocalDate.now();

        for (long i = 1; i <= 1000; i++) {
            Profile profile = Profile.builder()
                    .age(20 + random.nextInt(30)) // 20~49
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .height(150 + random.nextInt(30) + 0.0)
                    .weight(50 + random.nextInt(50) + 0.0)
                    .goal(Goal.values()[random.nextInt(Goal.values().length)])
                    .targetWeight(60.0)
                    .activityLevel(ActivityLevel.values()[random.nextInt(ActivityLevel.values().length)])
                    .build();

            RecommandCalorie change = RecommandCalorie.builder()
                    .userId(i)
                    .build();

            change.resetRecommendedCalorie(profile);
            change.resetCalorie();
            dailyCalorieChanges.add(change);

        }

        repository.saveAll(dailyCalorieChanges);
        calorieScoreRepository.saveAll(calorieScores);

        System.out.println("✅ 1000개의 DailyCalorieChange와 더미 저장 완료");
    }


    @Test
    @DisplayName("일일 칼로리 리셋 테스트")
    void DailyCalorieSummaryStepTest(){
        //given
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("dailyCalorieSummaryResetStep");
//        dailyCalorieSummaryScoreStep
        //when
        var keys = redisTemplate.keys("userId::*");
        //then
        assertThat(keys.size()).isEqualTo(1000);
        redisTemplate.delete(keys);

        keys = redisTemplate.keys("userId::*");
        assertThat(keys.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("스코어 정산 테스트")
    void DailyCalorieSummaryScoreTest(){
        //given
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("dailyCalorieSummaryResetStep");
        var keys = redisTemplate.keys("userId::*");
        assertThat(keys.size()).isEqualTo(1000);
        //when
        jobLauncherTestUtils.launchStep("dailyCalorieSummaryScoreStep");
        //then
        long size = calorieScoreRepository.count();
        assertThat(size).isEqualTo(1000);
        redisTemplate.delete(keys);
        keys = redisTemplate.keys("userId::*");
        assertThat(keys.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("티어 정산 테스트")
    void DailyCalorieSummaryTierTest(){
        //given
        jobLauncherTestUtils.launchStep("dailyCalorieSummaryTierStep");
        //when
        long size = dailyTierRepository.count();
        //then
        assertThat(size).isEqualTo(1000);
    }

    @Test
    @DisplayName("")
    void DailyCalorieSummaryJobTest() throws Exception {
        //given
        jobLauncherTestUtils.launchJob();
        //when
        long tierSize = dailyTierRepository.count();
        //then
        long scoreSize = calorieScoreRepository.count();

        assertThat(tierSize).isEqualTo(1000);
        assertThat(scoreSize).isEqualTo(1000);
    }

    @Test
    @DisplayName("")
    void DailyCalorieSummarySchedulerTest(){
        //given

        //when

        //then
    }





}
