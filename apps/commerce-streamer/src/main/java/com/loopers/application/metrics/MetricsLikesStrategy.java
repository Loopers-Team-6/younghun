package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.LikeMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Component;

@Component
public class MetricsLikesStrategy implements MetricsStrategy {
  private final MetricsRepository repository;
  private final MessageConvert convert;

  public MetricsLikesStrategy(MetricsRepository repository, MessageConvert convert) {
    this.repository = repository;
    this.convert = convert;
  }

  @Override
  public void process(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    String payload = convertMessage.getPayload();
    LikeMetricsMessage result = convert.convert(payload, LikeMetricsMessage.class);
    repository.upsertLikes(result.productId(), result.data());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.LIKES;
  }
}
