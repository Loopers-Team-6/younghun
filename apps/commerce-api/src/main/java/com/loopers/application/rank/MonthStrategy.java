package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.RankProjectionQuery;
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
public class MonthStrategy implements DateStrategy {
  private final MonthlyProductRankRepository monthlyProductRankRepository;
  private final ProductRepository productRepository;

  @Override
  public RankProjectionQuery process(LocalDate date, int page, int size) {
    int total = monthlyProductRankRepository.total(date);

    List<Long> rakingIds = monthlyProductRankRepository.get(date, page, size).stream().map(MonthlyProductRank::getRankId)
        .map(RankId::getProductId).toList();
    return new RankProjectionQuery(productRepository.getProductInfos(rakingIds), total);
  }

  @Override
  public DateType getType() {
    return DateType.MONTH;
  }
}
