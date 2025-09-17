package com.loopers.domain.catalog.product.stock;

import com.loopers.domain.RootMessage;

public interface StockPublisher {
  void evict(Long productId);
  void aggregate(Long productId, Long quantity);
  void aggregate(RootMessage message, Long productId);
}
