package com.loopers.application.metrics;

import org.springframework.stereotype.Component;

@Component
public abstract class MetricsStrategy {
  private final RankingRepository rankingRepository;

  protected MetricsStrategy(RankingRepository rankingRepository) {
    this.rankingRepository = rankingRepository;
  }

  abstract public void process(String message);

  abstract MetricsMethod method();

  public void increment(Long productId, double weight, long value) {
    rankingRepository.increment(productId, weight * value);
  }
}
