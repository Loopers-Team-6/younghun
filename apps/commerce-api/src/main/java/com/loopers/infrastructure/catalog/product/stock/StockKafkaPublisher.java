package com.loopers.infrastructure.catalog.product.stock;

import com.loopers.domain.catalog.product.stock.StockPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockKafkaPublisher implements StockPublisher {
  private final KafkaTemplate<Object, Object> kafkaTemplate;
  private final static String EVICT_TOPIC = "PRODUCT_STOCK_EVICT_V1";
  private final static String AGGREGATE_TOPIC = "PRODUCT_STOCK_CHANGED_V1";

  @Override
  public void evict(String key, Long productId) {
    log.info("카프카를 통해 재고정보가 전송이 되었습니다.");
    kafkaTemplate.send(EVICT_TOPIC, String.valueOf(productId), key + productId);
  }

  @Override
  public void aggregate(Long productId) {
    kafkaTemplate.send(AGGREGATE_TOPIC, String.valueOf(productId), 1);
  }

}
