package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.SalesMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Component;

@Component
public class MetricsSalesStrategy implements MetricsStrategy {
  private final MessageConvert convert;
  private final MetricsRepository repository;

  public MetricsSalesStrategy(MessageConvert convert, MetricsRepository repository) {
    this.convert = convert;
    this.repository = repository;
  }

  @Override
  public void process(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    SalesMetricsMessage result = convert.convert(convertMessage.getPayload(), SalesMetricsMessage.class);
    repository.upsertSales(result.productId(), result.quantity());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.SALES;
  }
}
