package com.loopers.application.catalog.product;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record ProductGetInfo(
    Long productId,
    Long rank,
    String brandName,
    String productName,
    BigInteger price,
    int likedCount,
    String description,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt
) {
}
