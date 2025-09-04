package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.ViewsMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Component;

@Component
public class MetricsViewsStrategy implements MetricsStrategy {
  private final MessageConvert convert;
  private final MetricsRepository repository;

  public MetricsViewsStrategy(MessageConvert convert, MetricsRepository repository) {
    this.convert = convert;
    this.repository = repository;
  }

  @Override
  public void process(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    ViewsMetricsMessage result = convert.convert(convertMessage.getPayload(), ViewsMetricsMessage.class);
    repository.upsertViews(result.productId(), result.value());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.VIEWS;
  }
}
