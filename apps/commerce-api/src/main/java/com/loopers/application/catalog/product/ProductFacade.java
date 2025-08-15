package com.loopers.application.catalog.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.cache.ProductCacheRepository;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacade {
  private final ProductRepository productRepository;
  private final ProductCacheRepository cacheRepository;

  /*
  latest, price_asc, likes_desc
   */

  // 목록 조회
  public ProductSearchInfo search(ProductCommand command) {
    String key = "product:rank:10";
    PageRequest page = PageRequest.of(command.currentPage(), command.perSize());
    String value = cacheRepository.get(key);
    Page<ProductProjection> search;
    if (value == null) {
      search = productRepository.search(command.toCriteria(), page);
    } else {
      try {
        List<ProductProjection> cacheContent = new ObjectMapper().readValue(value, new TypeReference<>() {
        });
        search = new PageImpl<>(cacheContent, Pageable.ofSize(10), 1000000);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    List<ProductProjection> products = search.getContent();
    return
        ProductSearchInfo.builder()
            .contents(products.stream().map(p -> ProductContents.of(
                    p.getName(), p.getId(), p.getBrandName(), p.getLikedCount(), p.getPrice()
                ))
                .collect(Collectors.toList()))
            .page(search.getNumber())
            .size(search.getSize())
            .totalPages(search.getTotalPages())
            .totalElements(search.getTotalElements())
            .build();
  }

  // 상세 조회
  public ProductGetInfo get(Long id) {
    ProductProjection productProjection = productRepository.get(id);

    return ProductGetInfo.builder()
        .productId(productProjection.getId())
        .productName(productProjection.getName())
        .brandName(productProjection.getBrandName())
        .price(productProjection.getPrice())
        .description(productProjection.getDescription())
        .likedCount(productProjection.getLikedCount())
        .build();
  }

  public void rank() {
    String key = "product:rank:10";
    ObjectMapper objectMapper = new ObjectMapper();
    String productRankValue = cacheRepository.get(key);
    if (productRankValue != null) {
      return;
    }

    ProductCommand command = ProductCommand.builder()
        .perSize(10)
        .currentPage(0)
        .sort(SortOption.LIKES_DESC)
        .build();

    Page<ProductProjection> search = productRepository.search(command.toCriteria(), PageRequest.of(0, 10));
    String value = null;
    try {
      value = objectMapper.writeValueAsString(search.getContent());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    cacheRepository.put(key, value, Duration.ofMinutes(10));

  }
}
