package com.loopers.infrastructure.rank;

import com.loopers.domain.rank.RankingRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRankingRepository implements RankingRepository {
  private final static String KEY = "ranking:all:";
  private final RedisTemplate<String, String> redisTemplate;

  public List<Long> range(int start, int end) {
    String newKey = KEY + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange(newKey, start, end))
        .stream().map(Long::parseLong).collect(Collectors.toList());
  }
}
