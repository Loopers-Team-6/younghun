package com.loopers.domain.mv;

import java.util.List;

public interface MonthlyProductRankRepository {
  List<MonthlyProductRank> addAll(List<MonthlyProductRank> rank);
}
