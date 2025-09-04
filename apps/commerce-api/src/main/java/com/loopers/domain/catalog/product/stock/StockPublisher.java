package com.loopers.domain.catalog.product.stock;

public interface StockPublisher {
  void evict(String key, Long productId);
  void aggregate(Long productId);
}
