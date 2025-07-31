package com.loopers.application.catalog.product;

import com.loopers.domain.catalog.product.ProductModel;
import com.loopers.domain.catalog.product.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
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
  public ProductSearchInfo search(ProductCommand command) {
    PageRequest page = PageRequest.of(command.currentPage(), command.perSize());
    Page<ProductModel> search = productRepository.search(command.toCriteria(), page);
    List<ProductModel> products = search.getContent();
    return
        ProductSearchInfo.builder()
            .contents(products.stream().map(p -> ProductContents.of(
                    p.getName(), p.getBrandId(), p.getPrice().getPrice(), p.getCreatedAt(), p.getUpdatedAt()
                ))
                .collect(Collectors.toList()))
            .build();
  }

  // 상세 조회
  public ProductGetInfo get(Long id) {
    ProductModel productModel = productRepository.get(id);
    return ProductGetInfo.builder()
        .productId(productModel.getId())
        .productName(productModel.getName())
        .price(productModel.getPrice().getPrice())
        .createdAt(productModel.getCreatedAt())
        .description(productModel.getDescription())
        .updatedAt(productModel.getUpdatedAt())
        .build();
  }

}
