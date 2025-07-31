package com.loopers.infrastructure.like.count;

import com.loopers.domain.like.count.ProductSignalCountModel;
import com.loopers.domain.like.count.ProductSignalCountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductSignalCountRepositoryImpl implements ProductSignalCountRepository {
  private final ProductSignalCountJpaRepository repository;

  public Optional<ProductSignalCountModel> find(Long productId) {
    return repository.findByProductId(productId);
  }

  @Override
  public void save(ProductSignalCountModel productSignalCountModel) {
    repository.save(productSignalCountModel);
  }
}
