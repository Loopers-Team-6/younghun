package com.loopers.domain.like.product;

import java.util.Optional;

public interface LikedProductRepository {
  Optional<LikedProductModel> find(Long productId);
  void save(LikedProductModel likedProductModel);
}
