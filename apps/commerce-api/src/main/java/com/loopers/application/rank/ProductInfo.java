package com.loopers.application.rank;

import com.loopers.domain.rank.ProductWithTrend;
import java.util.List;

public record ProductInfo(
    List<Contents> contents,
    int page,
    int size,
    int total
) {

  public static ProductInfo from(List<ProductWithTrend> productsWithTrend, int page, int size, int total) {
    List<Contents> list = productsWithTrend.stream()
        .map(p -> new Contents(
            p.product().getBrandId(),
            p.product().getBrandName(),
            p.product().getId(),
            p.product().getName(),
            p.todayRank(),
            p.diff(),
            p.status()
        ))
        .toList();
    return new ProductInfo(list, page, size, total);
  }

}

