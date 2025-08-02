package com.pknu.caloriepay.domain.user.dao;

import com.pknu.caloriepay.domain.user.application.RankInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRankingRedisRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String KEY = "member::score::ranking";
    private static final long limit = 100L;
    private static final Duration ttl = Duration.ofDays(1);

    public void add(long userId, int score){
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            conn.zAdd(KEY, score, String.valueOf(userId));
            conn.zRemRange(KEY, 0, -limit-1);
            conn.expire(KEY, ttl.toSeconds());
            return null;
        });
    }

    public void delete(){
        redisTemplate.delete(KEY);
    }

    public List<RankInfo> findAll() {
        AtomicInteger rank = new AtomicInteger(0);
        AtomicInteger prevScore = new AtomicInteger(Integer.MIN_VALUE);

        return Objects.requireNonNull(redisTemplate.opsForZSet()
                .reverseRangeWithScores(KEY, 0, -1)).stream()
                    .map(tuple -> {
                        long userId = Long.parseLong(Objects.requireNonNull(tuple.getValue()));
                        int score = Objects.requireNonNull(tuple.getScore()).intValue();

                        if(prevScore.get() != score){
                            rank.incrementAndGet();
                        }

                        prevScore.set(score);

                        return new RankInfo(userId, rank.get(), score);
                    })
                .toList();
    }

    public RankInfo findRank(Long userId){
        List<RankInfo> ranks = this.findAll();
        return ranks.stream()
                .filter(r -> r.userId() == userId)
                .findFirst()
                .map(rankInfo -> new RankInfo(rankInfo.userId(), rankInfo.rank(),  rankInfo.score()))
                .orElse(null);
    }
}
