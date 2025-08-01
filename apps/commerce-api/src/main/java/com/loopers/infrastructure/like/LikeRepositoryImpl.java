package com.loopers.infrastructure.like;

import com.loopers.domain.like.LikeModel;
import com.loopers.domain.like.LikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {
  private final LikeJpaRepository repository;

  public Optional<LikeModel> find(String userId, Long productId) {
    return repository.findByUserIdAndProductId(userId, productId);
  }

  public void save(LikeModel likeModel) {
    repository.save(likeModel);
  }


}
