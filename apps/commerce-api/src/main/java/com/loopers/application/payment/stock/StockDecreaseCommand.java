package com.loopers.application.payment.stock;

public record StockDecreaseCommand(Long productId, Long quantity) {
}
