package com.loopers.application.like;

public interface LikeAggregatePublisher {
  void aggregate(Long productId, int data);
}
