package com.pknu.caloriepay.domain.score.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalorieRedisRepository {

    private final StringRedisTemplate stringRedisTemplate;


}
