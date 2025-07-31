package com.loopers.infrastructure.like.count;

import com.loopers.domain.like.product.LikedProductModel;
import com.loopers.domain.like.product.LikedProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikedProductRepositoryImpl implements LikedProductRepository {
  private final LikedProductJpaRepository repository;

  public Optional<LikedProductModel> find(Long productId) {
    return repository.findByProductId(productId);
  }

  @Override
  public void save(LikedProductModel likedProductModel) {
    repository.save(likedProductModel);
  }
}
