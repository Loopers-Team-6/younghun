package com.loopers.infrastructure.metrics;

import com.loopers.application.metrics.RankingRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.zset.DefaultTuple;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRankingRepository implements RankingRepository {

  private final static String KEY = "ranking:all:";

  private final RedisTemplate<String, String> redisTemplate;

  private final StringRedisTemplate stringRedisTemplate;

  public RedisRankingRepository(RedisTemplate<String, String> redisTemplate, StringRedisTemplate stringRedisTemplate) {
    this.redisTemplate = redisTemplate;
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public void increment(Long productId, Double score) {
    String newKey = KEY + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    stringRedisTemplate.opsForZSet().incrementScore(newKey, String.valueOf(productId), score);
    stringRedisTemplate.expire(newKey, Duration.ofDays(2));
  }

  @Override
  public void increment(Map<Long, Long> aggregate, double weight) {
    String newKey = KEY + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    int batchSize = 100000; // 청크 크기
    List<Entry<Long, Long>> entries = new ArrayList<>(aggregate.entrySet());

    stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
      StringRedisConnection redisConnection = (StringRedisConnection) connection;

      for(int i = 0; i < entries.size(); i += batchSize) {
        int end = Math.min(i + batchSize, entries.size());
        List<Map.Entry<Long, Long>> batch = entries.subList(i, end);
        Set<Tuple> tupleSet = new HashSet<>();

        for (Map.Entry<Long, Long> entry : batch) {
          Long productId = entry.getKey();
          Long sum = entry.getValue();
          double score = sum * weight;
          tupleSet.add(new DefaultTuple(productId.toString().getBytes(), score));
        }
        // ZADD NX INCR 과 유사하게 동작시킬 수 있음
        redisConnection.zAdd(newKey.getBytes(), tupleSet);
      }
      redisConnection.expire(newKey, Duration.ofDays(2).getSeconds());
      return null;
    });


  }

}
