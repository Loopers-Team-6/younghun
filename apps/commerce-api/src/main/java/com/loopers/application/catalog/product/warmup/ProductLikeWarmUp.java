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
  private final ProductWarmupProcessor warmupProcessor;

  @Scheduled(cron = "* */5 * * * *")
  public void runTaskWithCron() {
    log.info("Cron 스케쥴 작업 실행: {}", LocalDateTime.now());
    warmupProcessor.warmup();
  }
}
