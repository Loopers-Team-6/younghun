package com.loopers.domain.like;

import java.util.Optional;

public interface LikeRepository {

  Optional<LikeModel> find(String userId, Long productId);
  void save(LikeModel likeModel);
}
