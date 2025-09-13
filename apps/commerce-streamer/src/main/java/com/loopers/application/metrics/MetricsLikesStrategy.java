package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.LikeMetricsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.rank.RankRepository;
import com.loopers.domain.weight.WeightRepository;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MetricsLikesStrategy extends MetricsStrategy {
  private final MetricsPublisher publisher;

  public MetricsLikesStrategy(RankingRepository rankingRepository,
                              EventHandledRepository eventHandledRepository,
                              WeightRepository weightRepository,
                              MessageConvert convert, RankRepository rankRepository, MetricsPublisher publisher) {
    super(rankingRepository, eventHandledRepository, weightRepository, convert, rankRepository);
    this.publisher = publisher;
  }


  @Override
  public MetricsMethod method() {
    return MetricsMethod.LIKES;
  }

  @Override
  public void sum(List<String> values) {
    Map<Long, Long> map = process(values).stream()
        .filter(LikeMetricsMessage.class::isInstance)  // BaseMessage 중 LikeMetricsMessage 선택
        .map(LikeMetricsMessage.class::cast)
        .collect(groupingBy(LikeMetricsMessage::productId, Collectors.summingLong(LikeMetricsMessage::data)));

//    publisher.likes(map);
    increment(map, weight().getLikes());
  }
}
