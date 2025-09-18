package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.MonthlyProductRankRepository;
import com.loopers.domain.mv.RankId;
import com.loopers.infrastructure.mv.DateType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthStrategy implements DateStrategy{
  private final MonthlyProductRankRepository monthlyProductRankRepository;
  private final ProductRepository productRepository;

  @Override
  public List<ProductProjection> process(LocalDate date, int page, int size) {
    List<Long> rakingIds = monthlyProductRankRepository.get(date, page, size).stream().map(MonthlyProductRank::getRankId)
        .map(RankId::getProductId).toList();
    return productRepository.getProductInfos(rakingIds);
  }

  @Override
  public DateType getType() {
    return DateType.MONTH;
  }
}
