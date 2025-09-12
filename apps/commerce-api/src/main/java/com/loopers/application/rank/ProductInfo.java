package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductModel;
import java.util.List;

public record ProductInfo(
    List<Contents> contents,
    int page,
    int size,
    int total
) {

  public static ProductInfo from(List<ProductModel> productModels, int page, int size, int total) {
    List<Contents> list = productModels.stream()
        .map(a -> new Contents(a.getId(), a.getName()))
        .toList();
    return new ProductInfo(list, page, size, total);
  }

}

