package com.pknu.caloriepay.domain.user.dao;

import com.pknu.caloriepay.domain.user.application.RankInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class MemberRankingRedisRepositoryTest {

    @Autowired
    private MemberRankingRedisRepository repository;

    private Map<Long, Integer> dummyData;

    @BeforeEach
    void setUp() {
        dummyData = new LinkedHashMap<>();
        for (long userId = 1; userId <= 150; userId++) {
            dummyData.put(userId, (int)(Math.random() * 1000));
        }
        dummyData.put(7777L, 11111);
        dummyData.put(4444L, -2);
        repository.delete();
    }

    @Test
    void insertTest() {
        dummyData.forEach((userId, score) -> {
            repository.add(userId, score);
        });

        List<RankInfo> result = repository.findAll();

        assertThat(result.size()).isLessThanOrEqualTo(100);
        assertThat(result.get(0).userId()).isEqualTo(7777L);// 랭킹 제한 확인
    }

    @Test
    void findRankTest() {

        dummyData.forEach((userId, score) -> {
            repository.add(userId, score);
        });

        System.out.println(repository.findRank(7777L));
        assertThat(repository.findRank(7777L).rank()).isEqualTo(1L);
        assertThat(repository.findRank(4444L)).isEqualTo(null);
    }
}
