package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.mv.RankId;
import com.loopers.domain.mv.WeeklyProductRank;
import com.loopers.domain.mv.WeeklyProductRankRepository;
import com.loopers.infrastructure.mv.DateType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeekStrategy implements DateStrategy{
  private final WeeklyProductRankRepository weeklyProductRankRepository;
  private final ProductRepository productRepository;


  @Override
  public List<ProductProjection> process(LocalDate date, int page, int size) {
    List<Long> rakingIds = weeklyProductRankRepository.get(date, page, size).stream().map(WeeklyProductRank::getRankId)
        .map(RankId::getProductId).toList();
    return productRepository.getProductInfos(rakingIds);
  }

  @Override
  public DateType getType() {
    return DateType.WEEK;
  }
}
