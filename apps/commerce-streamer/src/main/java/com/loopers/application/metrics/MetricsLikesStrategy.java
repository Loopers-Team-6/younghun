package com.loopers.application.metrics;

import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.LikeMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import org.springframework.stereotype.Component;

@Component
public class MetricsLikesStrategy extends MetricsStrategy {
  private final MetricsRepository repository;
  private final EventHandledRepository eventHandledRepository;
  private final MessageConvert convert;

  public MetricsLikesStrategy(MetricsRepository repository, RankingRepository rankingRepository,
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
    String payload = convertMessage.getPayload();
    LikeMetricsMessage result = convert.convert(payload, LikeMetricsMessage.class);
    repository.upsertLikes(result.productId(), result.data());
    increment(result.productId(), 0.3, result.data());
    eventHandledRepository.save(convertMessage.getEventId());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.LIKES;
  }
}
