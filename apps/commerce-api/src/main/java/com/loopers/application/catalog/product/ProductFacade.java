package com.loopers.application.catalog.product;

import com.loopers.domain.catalog.product.ProductModel;
import com.loopers.domain.catalog.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacade {
  private final ProductRepository productRepository;


  /*
  latest, price_asc, likes_desc (구현 못함)
   */

  // 목록 조회
  public Page<ProductModel> search(ProductCommand command) {
    PageRequest page = PageRequest.of(command.currentPage(), command.perSize());

    return productRepository.search(
        command.toCriteria(), page
    );
  }

  // 상세 조회

}
