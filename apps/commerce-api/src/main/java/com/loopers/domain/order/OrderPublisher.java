package com.loopers.domain.order;

public interface OrderPublisher {
  void publish(String userId, Long orderId, Long couponId);
}
