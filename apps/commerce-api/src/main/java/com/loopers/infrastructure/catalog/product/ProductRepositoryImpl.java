package com.loopers.infrastructure.catalog.product;

import com.loopers.domain.catalog.product.ProductModel;
import com.loopers.domain.catalog.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
  private final ProductJpaRepository repository;

  public List<ProductModel> listOf(Long brandId) {
    return repository.findByBrandId(brandId);
  }
}
