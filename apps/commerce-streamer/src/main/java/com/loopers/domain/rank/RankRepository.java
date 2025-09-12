package com.loopers.domain.rank;

import java.util.Optional;

public interface RankRepository {
  Optional<Rank> get(Long productId);

  void save(Rank rank);

  void increment(Long productId, Double score);
}
