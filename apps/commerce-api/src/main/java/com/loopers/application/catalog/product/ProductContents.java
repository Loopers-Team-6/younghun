package com.loopers.application.catalog.product;

import java.math.BigInteger;
import java.time.ZonedDateTime;

public record ProductContents(
    String name,
    Long id,
    String brandName,
    int likeCount,
    BigInteger price,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt
) {

  public static ProductContents of(String name, Long id,
                                   String brandName,
                                   int likeCount,
                                   BigInteger price, ZonedDateTime createdAt,
                                   ZonedDateTime updatedAt) {
    return new ProductContents(name, id, brandName, likeCount, price, createdAt, updatedAt);
  }
}
