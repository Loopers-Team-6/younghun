package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.LikeMetricsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.weight.WeightRepository;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MetricsLikesStrategy extends MetricsStrategy {
  private final MetricsRepository repository;

  public MetricsLikesStrategy(MetricsRepository repository, RankingRepository rankingRepository,
                              EventHandledRepository eventHandledRepository,
                              WeightRepository weightRepository,
                              MessageConvert convert) {
    super(rankingRepository, eventHandledRepository, weightRepository, convert);
    this.repository = repository;
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

    // 파티션별로
    for (Entry<Long, Long> entry : map.entrySet()) {
      Long productId = entry.getKey();
      Long sum = entry.getValue();
      repository.upsertLikes(productId, sum);
      increment(productId, weight().getLikes(), sum);
    }
  }
}
