package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsRepository;
import org.springframework.stereotype.Component;

@Component
public class MetricsSalesStrategy implements MetricsStrategy {
  private final MetricsRepository repository;

  public MetricsSalesStrategy(MetricsRepository repository) {
    this.repository = repository;
  }

  @Override
  public void process(long productId, int value) {
    repository.upsertSales(productId, value);
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.SALES;
  }
}
