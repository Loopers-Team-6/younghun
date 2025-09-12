package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductModel;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.rank.RankingRepository;
import com.loopers.interfaces.api.rank.RankV1Dto.RankCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankFacade {
  private final RankingRepository rankingRepository;
  private final ProductRepository productRepository;


  public ProductInfo rank(RankCondition condition) {
    List<Long> rankingIds = rankingRepository.range(condition.page(), condition.size());

    List<ProductModel> models = productRepository.getIn(rankingIds);

    return ProductInfo.from(models, condition.page(), condition.size(), rankingRepository.total());
  }
}
