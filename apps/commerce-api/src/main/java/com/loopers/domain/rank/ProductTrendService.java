package com.loopers.domain.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductTrendService {

  public List<ProductWithTrend> calculate(List<Long> rankingIds, int page, int size, Map<Long, Integer> previousRankMap,
                                          List<ProductProjection> models) {
    List<ProductWithTrend> productsWithTrend = new ArrayList<>();
    for (int i = 0; i < rankingIds.size(); i++) {
      Long id = rankingIds.get(i);
      int todayRank = (page * size) + (i + 1);
      Integer yesterdayRank = previousRankMap.get(id);

      String status;
      Integer diff = null;

      if (yesterdayRank != null) {
        diff = yesterdayRank - todayRank;
        if (diff > 0) {
          status = "UP";
        } else if (diff < 0) {
          status = "DOWN";
        } else {
          status = "SAME";
        }
      } else {
        status = "NEW";
      }

      ProductProjection model = models.stream()
          .filter(m -> m.getId().equals(id))
          .findFirst()
          .orElseThrow();

      productsWithTrend.add(new ProductWithTrend(model, todayRank, yesterdayRank, diff, status));
    }
    return productsWithTrend;
  }
}
