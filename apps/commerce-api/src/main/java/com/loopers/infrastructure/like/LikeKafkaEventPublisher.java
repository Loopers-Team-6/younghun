package com.loopers.infrastructure.like;

import com.loopers.application.like.LikeAggregatePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeKafkaEventPublisher implements LikeAggregatePublisher {
  private final KafkaTemplate<Object, Object> kafkaTemplate;
  private final static String TOPIC = "PRODUCT_LIKE_CHANGED_V1";

  public void aggregate(Long productId, int data) {
    log.info(" productId: {}, data: {}", productId, data);

    kafkaTemplate.send(TOPIC, String.valueOf(productId), data);
  }

}
