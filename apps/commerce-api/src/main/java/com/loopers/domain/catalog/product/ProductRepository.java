package com.loopers.domain.catalog.product;

import java.util.List;

public interface ProductRepository {
  List<ProductModel> listOf(Long brandId);
}
