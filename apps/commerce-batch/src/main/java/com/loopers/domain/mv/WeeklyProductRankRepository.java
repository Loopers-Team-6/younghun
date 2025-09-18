package com.loopers.domain.mv;

import java.util.List;

public interface WeeklyProductRankRepository {
  List<WeeklyProductRank> addAll(List<WeeklyProductRank> rank);
}
