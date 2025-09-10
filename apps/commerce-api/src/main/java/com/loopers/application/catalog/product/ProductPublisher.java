package com.loopers.application.catalog.product;

import com.loopers.domain.RootMessage;

public interface ProductPublisher {
  void aggregate(RootMessage message, Long productId);
}
