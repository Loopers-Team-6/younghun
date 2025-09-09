package com.loopers.application.catalog.product;

import java.math.BigInteger;

public interface StockEventPublisher {
  void decrease(Long productId, BigInteger unitPrice, Long quantity, Long current);
}
