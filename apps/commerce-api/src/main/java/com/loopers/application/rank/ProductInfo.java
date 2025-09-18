package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import java.util.List;

public record ProductInfo(
    List<Contents> contents,
    int page,
    int size,
    int total
) {

  public static ProductInfo from(List<ProductProjection> productsWithTrend, int page, int size, int total) {
    List<Contents> list = productsWithTrend.stream()
        .map(p -> new Contents(
            p.getBrandId(),
            p.getBrandName(),
            p.getId(),
            p.getName()
        ))
        .toList();
    return new ProductInfo(list, page, size, total);
  }

}

