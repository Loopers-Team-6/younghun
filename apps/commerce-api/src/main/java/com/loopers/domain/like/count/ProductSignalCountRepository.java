package com.loopers.domain.like.count;

import java.util.Optional;

public interface ProductSignalCountRepository {
  Optional<ProductSignalCountModel> find(Long productId);
  void save(ProductSignalCountModel productSignalCountModel);
}
