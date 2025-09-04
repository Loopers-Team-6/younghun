package com.loopers.interfaces.consumer;

import com.loopers.config.kafka.KafkaConfig;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogKafkaConsumer {
  @KafkaListener(
      topics = "${logging-kafka.like.topic-name}",
      groupId = "${logging-kafka.group-id}",
      containerFactory = KafkaConfig.BATCH_LISTENER
  )
  public void logListener(
      List<ConsumerRecord<String, String>> messages,
      Acknowledgment acknowledgment
  ) {
    System.out.println("Received batch: " + messages);

    System.out.println(acknowledgment);
    acknowledgment.acknowledge();
  }
}
