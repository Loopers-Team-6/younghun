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
  public List<Long> range(LocalDate date, int page, int size) {
    long start = ((long) page * size) + page;       // start
    long end = start + size - 1;           // end
    return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange(generateKey(date), start, end))
        .stream().map(Long::parseLong).collect(Collectors.toList());
  }

  @Override
  public int total(LocalDate date) {
    return Objects.requireNonNull(redisTemplate.opsForZSet().zCard(generateKey(date))).intValue();
  }

  @Override
  public Long getRank(Long productId) {
    return redisTemplate.opsForZSet().reverseRank(generateKey(LocalDate.now()), String.valueOf(productId));
  }

  private String generateKey(LocalDate date) {
    return KEY + date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }


}
