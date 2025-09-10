package com.loopers.application.catalog.product;

import com.loopers.support.shared.Message;

public interface ProductPublisher {
  void aggregate(Message message, Long productId);
}
