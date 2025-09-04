package com.loopers.infrastructure.catalog;

import com.loopers.application.catalog.product.ProductPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductKafkaPublisher implements ProductPublisher {
  private final KafkaTemplate<Object, Object> kafkaTemplate;
  private final static String AGGREGATE_TOPIC = "PRODUCT_VIEWS_CHANGED_V1";

  @Override
  public void aggregate(Long productId) {
    kafkaTemplate.send(AGGREGATE_TOPIC, String.valueOf(productId), 1);
  }
}
