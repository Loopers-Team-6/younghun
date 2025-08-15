package com.loopers.application.catalog.product.warmup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopers.application.catalog.product.ProductCommand;
import com.loopers.application.catalog.product.SortOption;
import com.loopers.domain.catalog.product.ProductProjection;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.cache.ProductCacheRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductLikeWarmUp {
  private final ProductRepository productRepository;
  private final ProductCacheRepository cacheRepository;
  private final ObjectMapper objectMapper;

  private final String KEY = "product:rank:10";

  @Scheduled(cron = "* */5 * * * *")
  public void runTaskWithCron() {
    log.info("Cron 스케쥴 작업 실행: {}", LocalDateTime.now());

    ProductCommand command = ProductCommand.builder()
        .sort(SortOption.LIKES_DESC)
        .build();
    PageRequest page = PageRequest.of(command.currentPage(), command.perSize());
    Page<ProductProjection> search = productRepository.search(command.toCriteria(), page);
    List<ProductProjection> content = search.getContent();

    // 내용물이 없으면 캐싱하지 않는다.
    if (content.isEmpty()) {
      return;
    }

    // 초기화
    cacheRepository.remove(KEY);

    String value;
    try {
      value = objectMapper.writeValueAsString(search.getContent());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    cacheRepository.put(KEY, value, Duration.ofMinutes(10));
  }
}
