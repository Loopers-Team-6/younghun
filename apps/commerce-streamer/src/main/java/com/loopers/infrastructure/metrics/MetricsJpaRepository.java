package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.MetricsModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MetricsJpaRepository extends JpaRepository<MetricsModel, Long>{

  @Query("""
        SELECT m FROM MetricsModel m
        WHERE m.productId = :productId
         AND m.date = CURRENT_DATE
        """)
  Optional<MetricsModel> findByProductId(Long productId);

}
