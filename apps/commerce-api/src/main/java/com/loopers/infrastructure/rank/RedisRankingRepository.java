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

  @Override
  public List<Long> range(int page, int size) {
    long start = ((long) page * size) + page;       // start
    long end = start + size - 1;           // end
    return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange(generateKey(), start, end))
        .stream().map(Long::parseLong).collect(Collectors.toList());
  }

  @Override
  public int total() {
    return Objects.requireNonNull(redisTemplate.opsForZSet().zCard(generateKey())).intValue();
  }

  @Override
  public Long getRank(Long productId) {
    return redisTemplate.opsForZSet().reverseRank(generateKey(), String.valueOf(productId));
  }

  private String generateKey() {
    return KEY + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }


}
