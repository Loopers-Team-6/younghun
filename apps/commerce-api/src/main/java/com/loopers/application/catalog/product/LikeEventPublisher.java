package com.loopers.application.catalog.product;

public interface LikeEventPublisher {
  void increase(String userId, Long productId);

  void decrease(String userId, Long productId);
}
