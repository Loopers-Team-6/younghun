package com.loopers.domain.mv;

import java.time.LocalDate;
import java.util.List;

public interface MonthlyProductRankRepository {
  List<MonthlyProductRank> get(LocalDate date);
}
