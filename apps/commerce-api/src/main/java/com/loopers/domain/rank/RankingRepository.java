package com.loopers.domain.rank;

import java.time.LocalDate;
import java.util.List;

public interface RankingRepository {
  List<Long> range(LocalDate date, int page, int size);

  Long getRank(Long productId);

  int total(LocalDate date);
}
