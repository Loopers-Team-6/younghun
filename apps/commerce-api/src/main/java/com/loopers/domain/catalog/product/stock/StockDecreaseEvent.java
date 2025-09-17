package com.loopers.domain.catalog.product.stock;

import java.math.BigInteger;

public record StockDecreaseEvent(
    Long productId,
    BigInteger unitPrice,
    Long quantity,
    Long current
) {
}
