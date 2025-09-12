package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.rank.ProductTrendService;
import com.loopers.domain.rank.ProductWithTrend;
import com.loopers.domain.rank.RankingRepository;
import com.loopers.interfaces.api.rank.RankV1Dto.RankCondition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankFacade {
  private final RankingRepository rankingRepository;
  private final ProductRepository productRepository;


  public ProductInfo rank(RankCondition condition) {
    int totalSize = rankingRepository.total(condition.date());
    List<Long> rankingIds = rankingRepository.range(condition.date(), condition.page(), condition.size());
    List<Long> previousRanking = rankingRepository.range(condition.date().minusDays(1), 0, totalSize);

    List<ProductProjection> models = productRepository.getProductInfos(rankingIds);

    // 전날 랭킹 Map<id, rank>
    Map<Long, Integer> previousRankMap = new HashMap<>();
    for (int i = 0; i < previousRanking.size(); i++) {
      previousRankMap.put(previousRanking.get(i), i + 1); // 1등부터 시작
    }


    List<ProductWithTrend> productsWithTrend =
        new ProductTrendService().calculate(rankingIds, condition.page(), condition.size(),
        previousRankMap, models);

    return ProductInfo.from(productsWithTrend, condition.page(), condition.size(), totalSize);
  }
}
