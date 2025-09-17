package com.loopers.application.metrics;

import java.util.Map;

public interface RankingRepository {
  void increment(Long productId, Double score);
  void increment(Map<Long, Long> aggregate, double weight);
}
