package com.loopers.domain.rank;

import com.loopers.domain.catalog.product.ProductProjection;

public record ProductWithTrend(
    ProductProjection product,
    Integer todayRank,
    Integer yesterdayRank,
    Integer diff,
    String status
) {
}
