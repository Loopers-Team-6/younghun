package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsRepository;
import org.springframework.stereotype.Component;

@Component
public class MetricsViewsStrategy implements MetricsStrategy {
  private final MetricsRepository repository;

  public MetricsViewsStrategy(MetricsRepository repository) {
    this.repository = repository;
  }

  @Override
  public void process(long productId, int value) {
    repository.upsertViews(productId, value);
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.VIEWS;
  }
}
