package com.loopers.domain;

public record ViewMetricsMessage(
    Long productId
    , int data
) implements BaseMessage {
}
