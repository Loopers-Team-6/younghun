package com.loopers.domain.catalog.product.stock;

import java.math.BigInteger;

public record StockMetricsMessage(
    Long productId,
    BigInteger price,
    Long quantity
) {
}
