package com.loopers.infrastructure.like.count;

import com.loopers.domain.like.product.LikedProductModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedProductJpaRepository extends JpaRepository<LikedProductModel, Long> {
  Optional<LikedProductModel> findByProductId(Long productId);
}
