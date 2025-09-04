package com.loopers.interfaces.consumer;

import com.loopers.config.kafka.KafkaConfig;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class EvictKafkaConsumer {
  @KafkaListener(
      topics = "${evict-kafka.like.topic-name}",
      groupId = "${evict-kafka.group-id}",
      containerFactory = KafkaConfig.BATCH_LISTENER
  )
  public void demoListener(
      List<ConsumerRecord<String, String>> messages,
      Acknowledgment acknowledgment
  ) {
    System.out.println("Received batch: " + messages);

    System.out.println(acknowledgment);
    acknowledgment.acknowledge();
  }
}
