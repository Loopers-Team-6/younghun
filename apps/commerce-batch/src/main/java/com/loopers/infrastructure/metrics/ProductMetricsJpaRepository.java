package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.ProductMetrics;
import io.lettuce.core.dynamic.annotation.Param;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductMetricsJpaRepository extends JpaRepository<ProductMetrics, Long> {
  @Query("""
      SELECT p
      FROM ProductMetrics p
      WHERE p.date between :startDate and :endDate
      """)
  Page<ProductMetrics> findByDateRange(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate, Pageable pageable);
}
