package com.loopers.application.like;

import com.loopers.support.shared.Message;

public interface LikePublisher {
  void aggregate(Message message, Long productId);
  void evict(Long productId);
}
