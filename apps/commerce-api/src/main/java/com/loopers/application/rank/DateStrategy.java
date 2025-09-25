package com.loopers.application.rank;

import com.loopers.domain.catalog.product.RankProjectionQuery;
import com.loopers.infrastructure.mv.DateType;
import java.time.LocalDate;

public interface DateStrategy {
  RankProjectionQuery process(LocalDate date, int page, int size);

  DateType getType();
}
