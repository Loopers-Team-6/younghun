package com.loopers.application.metrics;

import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.SalesMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Component;

@Component
public class MetricsSalesStrategy extends MetricsStrategy {
  private final MessageConvert convert;
  private final EventHandledRepository eventHandledRepository;
  private final MetricsRepository repository;

  public MetricsSalesStrategy(MetricsRepository repository, RankingRepository rankingRepository,
                              EventHandledRepository eventHandledRepository,
                              MessageConvert convert) {
    super(rankingRepository);
    this.repository = repository;
    this.eventHandledRepository = eventHandledRepository;
    this.convert = convert;
  }

  @Override
  public void process(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    SalesMetricsMessage result = convert.convert(convertMessage.getPayload(), SalesMetricsMessage.class);
    repository.upsertSales(result.productId(), result.quantity());
    eventHandledRepository.save(convertMessage.getEventId());
    increment(result.productId(), 0.7, result.quantity());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.SALES;
  }
}
