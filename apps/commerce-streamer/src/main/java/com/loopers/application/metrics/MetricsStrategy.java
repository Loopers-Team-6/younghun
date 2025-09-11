package com.loopers.application.metrics;

import com.loopers.domain.BaseMessage;
import com.loopers.domain.RootMeticsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.weight.Weight;
import com.loopers.domain.weight.WeightRepository;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class MetricsStrategy {
  private final RankingRepository rankingRepository;
  private final EventHandledRepository eventHandledRepository;
  private final WeightRepository weightRepository;
  private final MessageConvert convert;

  @Value("${weight.views}")
  private double views;
  @Value("${weight.sales}")
  private double sales;
  @Value("${weight.likes}")
  private double likes;


  protected MetricsStrategy(RankingRepository rankingRepository, EventHandledRepository eventHandledRepository,
                            WeightRepository weightRepository, MessageConvert convert) {
    this.rankingRepository = rankingRepository;
    this.eventHandledRepository = eventHandledRepository;
    this.weightRepository = weightRepository;
    this.convert = convert;
  }

  abstract MetricsMethod method();

  abstract public void sum(List<String> values);

  public List<BaseMessage> process(List<String> values) {
    List<RootMeticsMessage> messages = values.stream().map(a -> convert.convert(a, RootMeticsMessage.class)).toList();

    for (RootMeticsMessage message : messages) {
      eventHandledRepository.save(message.eventId());
    }
    return messages.stream().map(RootMeticsMessage::payload).toList();
  }

  public Weight weight() {
    return weightRepository.get()
        .orElse(new Weight(views, sales, likes));
  }

  public void increment(Long productId, double weight, long value) {
    rankingRepository.increment(productId, weight * value);
  }

  public void increment(Map<Long, Long> aggregate, double weight) {
    rankingRepository.increment(aggregate, weight);
  }


}
