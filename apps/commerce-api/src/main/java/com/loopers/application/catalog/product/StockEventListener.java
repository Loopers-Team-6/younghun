package com.loopers.application.catalog.product;

import com.loopers.domain.catalog.product.stock.StockDecreaseEvent;
import com.loopers.domain.catalog.product.stock.StockPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventListener {
  private final StockCacheRepository repository;
  private final StockPublisher stockPublisher;

  @Async
  @EventListener
  public void decrease(StockDecreaseEvent event) {
    Long cacheCurrent = repository.get(event.productId());
    // 캐시가 존재하지 않는 경우
    if (cacheCurrent == 0L) {
      repository.init(event.productId(), event.current() - event.quantity());
      return;
    }
    repository.decrease(event.productId(), event.quantity());
    //집계
    stockPublisher.aggregate(event.productId());
  }
}
