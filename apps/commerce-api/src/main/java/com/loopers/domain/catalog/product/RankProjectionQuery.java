package com.loopers.domain.catalog.product;

import java.util.List;

public record RankProjectionQuery(
    List<ProductProjection> items,
    int total
) {
}
