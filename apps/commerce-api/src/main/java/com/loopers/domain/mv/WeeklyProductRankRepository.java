package com.loopers.domain.mv;

import java.time.LocalDate;
import java.util.List;

public interface WeeklyProductRankRepository {
  List<WeeklyProductRank> get(LocalDate date);
}
