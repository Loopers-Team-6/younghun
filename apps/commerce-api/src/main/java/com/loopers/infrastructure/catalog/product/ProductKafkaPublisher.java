package com.loopers.infrastructure.catalog.product;

import com.loopers.application.catalog.product.ProductPublisher;
import com.loopers.domain.RootMessage;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductKafkaPublisher implements ProductPublisher {
  private final KafkaTemplate<Object, Object> kafkaAtLeastTemplate;
  private final static String AGGREGATE_TOPIC = "PRODUCT_VIEWS_CHANGED_V1";

  @Override
  public void aggregate(@Payload RootMessage message, Long productId) {
    String key = LocalDate.now().toEpochDay() + ":" + productId;
    kafkaAtLeastTemplate.send(AGGREGATE_TOPIC, key, message);
  }
}
