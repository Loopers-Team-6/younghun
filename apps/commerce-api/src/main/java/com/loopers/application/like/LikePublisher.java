package com.loopers.application.like;

import com.loopers.domain.RootMessage;

public interface LikePublisher {
  void aggregate(RootMessage message, Long productId);
  void evict(Long productId);
}
