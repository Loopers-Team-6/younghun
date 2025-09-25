package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.RankProjectionQuery;
import com.loopers.domain.rank.RankingRepository;
import com.loopers.infrastructure.mv.DateType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyRankStrategy implements DateStrategy {
  private final RankingRepository rankingRepository;
  private final ProductRepository productRepository;

  @Override
  public RankProjectionQuery process(LocalDate date, int page, int size) {
    int totalSize = rankingRepository.total(date);
    List<Long> rankingIds = rankingRepository.range(date, page, size);
    return new RankProjectionQuery(productRepository.getProductInfos(rankingIds), totalSize);
  }

  @Override
  public DateType getType() {
    return DateType.DAILY;
  }
}
