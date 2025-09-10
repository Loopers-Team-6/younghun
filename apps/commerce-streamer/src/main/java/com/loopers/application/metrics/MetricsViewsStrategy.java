package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.ViewMetricsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MetricsViewsStrategy extends MetricsStrategy {
  private final MessageConvert convert;
  private final EventHandledRepository eventHandledRepository;
  private final MetricsRepository repository;

  public MetricsViewsStrategy(RankingRepository rankingRepository, MessageConvert convert,
                              EventHandledRepository eventHandledRepository, MetricsRepository repository) {
    super(rankingRepository, eventHandledRepository, convert);
    this.repository = repository;
    this.eventHandledRepository = eventHandledRepository;
    this.convert = convert;
  }

  @Override
  public void process(String message) {
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
      repository.upsertSales(productId, sum);
      increment(productId, 0.1, sum);
    }
  }
}
