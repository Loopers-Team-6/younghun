package com.loopers.application.metrics;

public interface RankingRepository {
  void increment(Long productId, Double score);
}
