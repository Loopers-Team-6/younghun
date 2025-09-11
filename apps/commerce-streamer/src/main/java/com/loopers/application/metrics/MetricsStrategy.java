package com.loopers.application.metrics;

import com.loopers.domain.BaseMessage;
import com.loopers.domain.RootMeticsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public abstract class MetricsStrategy {
  private final RankingRepository rankingRepository;
  private final EventHandledRepository eventHandledRepository;
  private final MessageConvert convert;

  protected MetricsStrategy(RankingRepository rankingRepository, EventHandledRepository eventHandledRepository, MessageConvert convert) {
    this.rankingRepository = rankingRepository;
    this.eventHandledRepository = eventHandledRepository;
    this.convert = convert;
  }

  abstract public void process(String message);

  abstract MetricsMethod method();

  abstract public void sum(List<String> values);

  public List<BaseMessage> process(List<String> values) {
    List<RootMeticsMessage> messages = values.stream().map(a -> convert.convert(a, RootMeticsMessage.class)).toList();

    for (RootMeticsMessage message : messages) {
      eventHandledRepository.save(message.eventId());
    }
    return messages.stream().map(RootMeticsMessage::payload).toList();
  }

  public void increment(Long productId, double weight, long value) {
    rankingRepository.increment(productId, weight * value);
  }
}
