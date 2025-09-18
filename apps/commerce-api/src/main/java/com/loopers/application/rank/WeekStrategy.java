package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.RankProjectionQuery;
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
  public RankProjectionQuery process(LocalDate date, int page, int size) {
    int total = weeklyProductRankRepository.total(date);

    List<Long> rakingIds = weeklyProductRankRepository.get(date, page, size).stream().map(WeeklyProductRank::getRankId)
        .map(RankId::getProductId).toList();
    return new RankProjectionQuery(productRepository.getProductInfos(rakingIds),total) ;
  }

  @Override
  public DateType getType() {
    return DateType.WEEK;
  }
}
