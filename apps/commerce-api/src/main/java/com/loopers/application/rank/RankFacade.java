package com.loopers.application.rank;

import com.loopers.domain.catalog.product.RankProjectionQuery;
import com.loopers.interfaces.api.rank.RankV1Dto.RankCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankFacade {
  private final DateStrategyFactory factory;

  public ProductInfo rank(RankCondition condition) {
    DateStrategy strategy = factory.getStrategy(condition.type().name());
    RankProjectionQuery query = strategy.process(condition.date(), condition.page(), condition.size());
    return ProductInfo.from(query.items(), condition.page(), condition.size(), query.total());
  }
}
