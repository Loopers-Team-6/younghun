package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.ProductMetrics;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMetricsJpaRepository extends JpaRepository<ProductMetrics, Long> {
  List<ProductMetrics> findByDate(LocalDate date);
}
