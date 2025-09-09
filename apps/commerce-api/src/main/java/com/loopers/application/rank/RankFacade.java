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


  public List<ProductInfo> rank(RankCondition condition) {
    List<Long> range = rankingRepository.range(condition.start(), condition.end());
    return productRepository.getIn(range)
        .stream().map(p -> new ProductInfo(p.getId(), p.getName())).toList();
  }
}
