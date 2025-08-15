package com.loopers.application.catalog.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.cache.ProductCacheRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
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
    Page<ProductProjection> searchData = getSearchData(command);
    List<ProductProjection> products = searchData.getContent();
    return
        ProductSearchInfo.builder()
            .contents(products.stream().map(p -> ProductContents.of(
                    p.getName(), p.getId(), p.getBrandName(), p.getLikedCount(), p.getPrice()
                ))
                .collect(Collectors.toList()))
            .page(searchData.getNumber())
            .size(searchData.getSize())
            .totalPages(searchData.getTotalPages())
            .totalElements(searchData.getTotalElements())
            .build();
  }

  public Page<ProductProjection> getSearchData(ProductCommand command) {
    String key = "product:rank:10";
    PageRequest page = PageRequest.of(command.currentPage(), command.perSize());
    // 1. 캐시 사용 조건(좋아요순 정렬)이면서 캐시에 값이 실제로 존재할 때만 캐시 로직 실행
    if (command.sort() == SortOption.LIKES_DESC) {
      String value = cacheRepository.get(key);
      if (value != null) {
        log.info("cache hit");
        try {
          List<ProductProjection> cacheContent = new ObjectMapper().readValue(value, new TypeReference<>() {
          });
          // 캐시 처리 성공 시, 여기서 결과를 바로 반환하고 메서드를 종료합니다.
          return new PageImpl<>(cacheContent, Pageable.ofSize(10), 10);
        } catch (JsonProcessingException e) {
          throw new CoreException(ErrorType.INTERNAL_ERROR, "json parse error : " + e.getMessage());
        }
      }
      // '좋아요순'이지만 캐시에 값이 없는 경우 (cache miss)
      log.info("cache miss");
    }
    // 2. 위 if 블록의 조건을 만족하지 못한 모든 경우 (캐시를 사용하지 않는 정렬 or 캐시 미스)
    // 이 코드가 실행된다는 것은 DB 조회가 필요하다는 의미입니다.
    return productRepository.search(command.toCriteria(), page);
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
      log.info("TTL 강제 초기화");
      cacheRepository.remove(key);
    }

    ProductCommand command = ProductCommand.builder()
        .perSize(10)
        .currentPage(0)
        .sort(SortOption.LIKES_DESC)
        .build();

    Page<ProductProjection> search = productRepository.search(command.toCriteria(), PageRequest.of(0, 10));
    String value;
    try {
      value = objectMapper.writeValueAsString(search.getContent());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    log.info("warm up data : {}", value);
    cacheRepository.put(key, value, Duration.ofMinutes(10));

  }
}
