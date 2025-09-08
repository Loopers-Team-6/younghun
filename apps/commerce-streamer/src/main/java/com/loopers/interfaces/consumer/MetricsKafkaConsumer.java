package com.loopers.interfaces.consumer;

import com.loopers.application.metrics.MetricsMethod;
import com.loopers.application.metrics.MetricsStrategyFactory;
import com.loopers.config.kafka.KafkaConfig;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MetricsKafkaConsumer {
  private final MessageConvert convert;
  private final MetricsStrategyFactory factory;

  public MetricsKafkaConsumer(MessageConvert convert, MetricsStrategyFactory factory) {
    this.convert = convert;
    this.factory = factory;
  }

  @KafkaListener(
      topics = {
          "${aggregate-kafka.like.topic-name}",
      },
      groupId = "${aggregate-kafka.group-id}",
      containerFactory = KafkaConfig.BATCH_LISTENER
  )
  public void aggregateLikesListener(
      List<ConsumerRecord<String, String>> messages,
      Acknowledgment acknowledgment
  ) {

    for (ConsumerRecord<String, String> message : messages) {
      factory.getStrategy(MetricsMethod.LIKES).process(message.value());
    }

    acknowledgment.acknowledge();
  }

  @KafkaListener(
      topics = {
          "${aggregate-kafka.stock.topic-name}",
      },
      groupId = "${aggregate-kafka.group-id}",
      containerFactory = KafkaConfig.BATCH_LISTENER
  )
  public void aggregateStockListener(
      List<ConsumerRecord<String, String>> messages,
      Acknowledgment acknowledgment
  ) {

    for (ConsumerRecord<String, String> message : messages) {
      factory.getStrategy(MetricsMethod.SALES).process(message.value());
    }
    acknowledgment.acknowledge();
  }

  @KafkaListener(
      topics = {
          "${aggregate-kafka.views.topic-name}"
      },
      groupId = "${aggregate-kafka.group-id}",
      containerFactory = KafkaConfig.BATCH_LISTENER
  )
  public void aggregateViewsListener(
      List<ConsumerRecord<String, String>> messages,
      Acknowledgment acknowledgment
  ) {
    for (ConsumerRecord<String, String> message : messages) {
      factory.getStrategy(MetricsMethod.VIEWS).process(message.value());
    }
    acknowledgment.acknowledge();
  }
}
