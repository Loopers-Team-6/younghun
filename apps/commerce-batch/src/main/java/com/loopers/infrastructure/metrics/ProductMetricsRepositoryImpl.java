package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.ProductMetrics;
import com.loopers.domain.metrics.ProductMetricsRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductMetricsRepositoryImpl implements ProductMetricsRepository {
  private final ProductMetricsJpaRepository repository;

  public ProductMetricsRepositoryImpl(ProductMetricsJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<ProductMetrics> get(LocalDate date) {
    return repository.findByDate(date);
  }
}
