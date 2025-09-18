package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.interfaces.api.rank.RankV1Dto.RankCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankFacade {
  private final DateStrategyFactory factory;

  public ProductInfo rank(RankCondition condition) {

    DateStrategy strategy = factory.getStrategy(condition.type().name());
    List<ProductProjection> models = strategy.process(condition.date(), condition.page(), condition.size());

    return ProductInfo.from(models, condition.page(), condition.size(), 100);
  }
}
