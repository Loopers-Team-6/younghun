package com.loopers.domain.metrics;

public interface MetricsRepository {
  void upsertLikes(Long productId, long value, double weight);
  void upsertViews(Long productId, long value, double weight);
  void upsertSales(Long productId, long value, double weight);
}
