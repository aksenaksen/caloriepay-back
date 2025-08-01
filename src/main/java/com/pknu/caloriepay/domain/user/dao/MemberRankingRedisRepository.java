package com.pknu.caloriepay.domain.user.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    public Map<Long, Integer> findAll() {
        return Objects.requireNonNull(redisTemplate.opsForZSet()
                        .reverseRangeWithScores(KEY, 0, -1)).stream()
                .collect(Collectors.toMap(
                        tuple -> Long.parseLong(Objects.requireNonNull(tuple.getValue())),  // String -> Long 변환
                        tuple -> Objects.requireNonNull(tuple.getScore()).intValue(),
                        (oldV, newV) -> oldV,
                        LinkedHashMap::new
                ));
    }

    public Long findRank(Long userId){
        return redisTemplate.opsForZSet()
                .reverseRank(KEY, String.valueOf(userId));
    }
}
