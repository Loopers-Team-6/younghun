package com.loopers.application.catalog.product;

//TODO 이거 수정해야함..
public interface LikeEventPublisher {
  void increase(String userId, Long productId);

  void decrease(String userId, Long productId);
}
