package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.MetricsModel;
import com.loopers.domain.metrics.MetricsRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MetricsRepositoryImpl implements MetricsRepository {
  private final MetricsJpaRepository metricsJpaRepository;

  public MetricsRepositoryImpl(MetricsJpaRepository metricsJpaRepository) {
    this.metricsJpaRepository = metricsJpaRepository;
  }

  @Transactional
  public void upsertLikes(Long productId, long value) {
    Optional<MetricsModel> metricsModel = metricsJpaRepository.findByProductId(productId);
    if (metricsModel.isEmpty()) {
      metricsJpaRepository.save(new MetricsModel(productId, 0L, value, 0L, LocalDate.now()));
      return;
    }

    MetricsModel metrics = metricsModel.get();
    metrics.updateLikes();

    if (metrics.getLikes() < 0) {
      throw new IllegalArgumentException("좋아요는 0보다 작을 수 없습니다.");
    }
  }

  @Transactional
  public void upsertViews(Long productId, long value) {
    Optional<MetricsModel> metricsModel = metricsJpaRepository.findByProductId(productId);
    if (metricsModel.isEmpty()) {
      metricsJpaRepository.save(new MetricsModel(productId, value, 0L, 0L, LocalDate.now()));
      return;
    }

    MetricsModel metrics = metricsModel.get();
    metrics.updateViews();
  }

  @Transactional
  public void upsertSales(Long productId, long value) {
    Optional<MetricsModel> metricsModel = metricsJpaRepository.findByProductId(productId);
    if (metricsModel.isEmpty()) {
      metricsJpaRepository.save(new MetricsModel(productId, 0L, 0L, value, LocalDate.now()));
      return;
    }

    MetricsModel metrics = metricsModel.get();
    metrics.updateSales(value);

    if (metrics.getSales() < 0) {
      throw new IllegalArgumentException("판매량은 0보다 작을 수 없습니다.");
    }
  }

}
