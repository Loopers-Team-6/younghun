package com.loopers.infrastructure.metrics;

import com.loopers.application.metrics.RankingRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRankingRepository implements RankingRepository {

  private final static String KEY = "ranking:all:";

  private final RedisTemplate<String, String> redisTemplate;

  public RedisRankingRepository(RedisTemplate<String, String> rankingRedisTemplate) {
    this.redisTemplate = rankingRedisTemplate;
  }

  @Override
  public void increment(Long productId, Double score) {
    String newKey = KEY + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    redisTemplate.opsForZSet().incrementScore(newKey, String.valueOf(productId), score);
  }
}
