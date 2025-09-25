package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.ProductMetrics;
import com.loopers.domain.metrics.ProductAggregate;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductMetricsJpaRepository extends JpaRepository<ProductMetrics, Long> {
  @Query("""
        SELECT new com.loopers.domain.metrics.ProductAggregate (
        p.productId, SUM(p.score), SUM(p.views), SUM(p.likes),SUM(p.sales))
        FROM ProductMetrics p
        WHERE p.date >= :startDate AND p.date <= :endDate
        GROUP BY p.productId
        ORDER BY SUM(p.score) DESC, SUM(p.sales) DESC, SUM(p.likes) DESC, SUM(p.views) DESC
        """)
  Page<ProductAggregate> findByDateRange(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate, Pageable pageable);

}
