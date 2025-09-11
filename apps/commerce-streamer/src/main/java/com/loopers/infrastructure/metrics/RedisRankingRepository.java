package com.loopers.infrastructure.metrics;

import com.loopers.application.metrics.RankingRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
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
    redisTemplate.expire(newKey, Duration.ofDays(2));
  }

  @Override
  public void increment(Map<Long, Long> aggregate, double weight) {
    redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
      StringRedisConnection redisConnection = (StringRedisConnection) connection;

      for (Entry<Long, Long> entry : aggregate.entrySet()) {
        Long productId = entry.getKey();
        Long sum = entry.getValue();
        double score = sum * weight;
        redisConnection.zIncrBy(KEY, score, productId.toString());
      }

      return null;
    });


  }

}
