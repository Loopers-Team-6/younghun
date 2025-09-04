package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsRepository;
import org.springframework.stereotype.Component;

@Component
public class MetricsLikesStrategy implements MetricsStrategy {
  private final MetricsRepository repository;

  public MetricsLikesStrategy(MetricsRepository repository) {
    this.repository = repository;
  }

  @Override
  public void process(long productId, int value) {
    repository.upsertLikes(productId, value);
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.LIKES;
  }
}
