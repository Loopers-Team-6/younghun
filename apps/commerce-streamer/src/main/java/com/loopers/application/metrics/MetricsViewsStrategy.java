package com.loopers.application.metrics;

import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.ViewsMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Component;

@Component
public class MetricsViewsStrategy extends MetricsStrategy {
  private final MessageConvert convert;
  private final EventHandledRepository eventHandledRepository;
  private final MetricsRepository repository;

  public MetricsViewsStrategy(RankingRepository rankingRepository, MessageConvert convert,
                              EventHandledRepository eventHandledRepository, MetricsRepository repository) {
    super(rankingRepository);
    this.repository = repository;
    this.eventHandledRepository = eventHandledRepository;
    this.convert = convert;
  }

  @Override
  public void process(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    ViewsMetricsMessage result = convert.convert(convertMessage.getPayload(), ViewsMetricsMessage.class);
    repository.upsertViews(result.productId(), result.value());
    eventHandledRepository.save(convertMessage.getEventId());
    increment(result.productId(), 0.1, result.value());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.VIEWS;
  }
}
