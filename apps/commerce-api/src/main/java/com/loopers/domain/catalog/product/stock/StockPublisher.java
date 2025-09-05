package com.loopers.domain.catalog.product.stock;

public interface StockPublisher {
  void evict(Long productId);
  void aggregate(Long productId, Long quantity);
}
