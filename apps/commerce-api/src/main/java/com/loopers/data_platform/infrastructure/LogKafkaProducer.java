package com.loopers.data_platform.infrastructure;

import com.loopers.data_platform.application.LogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogKafkaProducer implements LogProducer {
  private final KafkaTemplate<Object, Object> kafkaTemplate;

  private final static String TOPIC = "LOGGING_V1";

  public void send(String message) {
    kafkaTemplate.send(TOPIC, message);
  }
}
