package com.loopers.domain.rank;

import java.util.List;

public interface RankingRepository {
  List<Long> range(int start, int end);
  Long getRank(Long productId);
}
