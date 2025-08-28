package com.loopers.application.like;

public interface LikeEventPublisher {
  void increase(String userId, Long productId);

  void decrease(String userId, Long productId);
}
