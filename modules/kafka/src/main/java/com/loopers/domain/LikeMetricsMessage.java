package com.loopers.domain;

public record LikeMetricsMessage(
    Long productId,
    int data
) implements BaseMessage {
}
