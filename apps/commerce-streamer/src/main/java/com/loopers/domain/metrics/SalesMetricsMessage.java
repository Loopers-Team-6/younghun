package com.loopers.domain.metrics;

import java.math.BigInteger;

public record SalesMetricsMessage(
    Long productId,
    BigInteger unitPrice,
    Integer quantity
) {
}
