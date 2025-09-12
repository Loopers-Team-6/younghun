package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import java.util.List;

public record ProductInfo(
    List<Contents> contents,
    int page,
    int size,
    int total
) {

  public static ProductInfo from(List<ProductProjection> productModels, int page, int size, int total) {
    List<Contents> list = productModels.stream()
        .map(a -> new Contents(a.getBrandId(), a.getBrandName(), a.getId(), a.getName()))
        .toList();
    return new ProductInfo(list, page, size, total);
  }

}

