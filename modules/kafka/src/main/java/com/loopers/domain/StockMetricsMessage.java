package com.loopers.domain;

import java.math.BigInteger;

public record StockMetricsMessage(
    Long productId,
    BigInteger price,
    Long quantity
) implements BaseMessage {
}
