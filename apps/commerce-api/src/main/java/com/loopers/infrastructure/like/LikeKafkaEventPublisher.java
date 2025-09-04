package com.loopers.infrastructure.like;

import com.loopers.application.like.LikePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeKafkaEventPublisher implements LikePublisher {
  private final KafkaTemplate<Object, Object> kafkaTemplate;
  private final static String AGGREGATE_TOPIC = "PRODUCT_LIKE_CHANGED_V1";
  private final static String EVICT_TOPIC = "PRODUCT_LIKE_EVICT_V1";

  public void aggregate(Long productId, int data) {
    log.info(" productId: {}, data: {}", productId, data);

    kafkaTemplate.send(AGGREGATE_TOPIC, String.valueOf(productId), data);
  }


  public void evict(Long productId, String key) {
    kafkaTemplate.send(EVICT_TOPIC, String.valueOf(productId), key);
  }
}
