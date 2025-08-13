package com.loopers.domain.catalog.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
  List<ProductModel> listOf(Long brandId);
  Page<ProductProjection> search(ProductCriteria criteria, Pageable pageable);
  ProductProjection get(Long productId);

  List<ProductModel> getIn(List<Long> productIds);

  Optional<ProductModel> has(Long productId);

  Optional<ProductModel> hasWithLock(Long productId);

}
