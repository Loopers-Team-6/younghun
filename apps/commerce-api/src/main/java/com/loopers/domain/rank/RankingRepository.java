package com.loopers.domain.rank;

import java.util.List;

public interface RankingRepository {
  List<Long> range(int page, int size);
  Long getRank(Long productId);
  int total();
}
