package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.ViewMetricsMessage;
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
public class MetricsViewsStrategy extends MetricsStrategy {
  private final MetricsRepository repository;

  public MetricsViewsStrategy(RankingRepository rankingRepository, MessageConvert convert,
                              EventHandledRepository eventHandledRepository,
                              WeightRepository weightRepository,
                              MetricsRepository repository) {
    super(rankingRepository, eventHandledRepository, weightRepository, convert);
    this.repository = repository;
  }


  @Override
  public MetricsMethod method() {
    return MetricsMethod.VIEWS;
  }

  @Override
  public void sum(List<String> values) {
    Map<Long, Long> map = process(values).stream()
        .filter(ViewMetricsMessage.class::isInstance)  // BaseMessage 중 LikeMetricsMessage 선택
        .map(ViewMetricsMessage.class::cast)
        .collect(groupingBy(ViewMetricsMessage::productId, Collectors.summingLong(ViewMetricsMessage::data)));

    // 파티션별로
    for (Entry<Long, Long> entry : map.entrySet()) {
      Long productId = entry.getKey();
      Long sum = entry.getValue();
      repository.upsertViews(productId, sum);
      increment(productId, weight().getViews(), sum);
    }
  }
}
