package com.loopers.application.rank;

import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.infrastructure.mv.DateType;
import java.time.LocalDate;
import java.util.List;

public interface DateStrategy {
  List<ProductProjection> process(LocalDate date, int page, int size);

  DateType getType();
}
