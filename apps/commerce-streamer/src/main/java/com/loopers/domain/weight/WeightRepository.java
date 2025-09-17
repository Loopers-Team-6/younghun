package com.loopers.domain.weight;

import java.util.Optional;

public interface WeightRepository {
  Optional<Weight> get();
}
