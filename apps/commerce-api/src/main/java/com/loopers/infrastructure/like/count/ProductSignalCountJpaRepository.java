package com.loopers.infrastructure.like.count;

import com.loopers.domain.like.count.ProductSignalCountModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSignalCountJpaRepository extends JpaRepository<ProductSignalCountModel, Long> {
  Optional<ProductSignalCountModel> findByProductId(Long productId);
}
