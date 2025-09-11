package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.StockMetricsMessage;
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
public class MetricsSalesStrategy extends MetricsStrategy {
  private final MetricsRepository repository;

  public MetricsSalesStrategy(MetricsRepository repository, RankingRepository rankingRepository,
                              EventHandledRepository eventHandledRepository,
                              WeightRepository weightRepository,
                              MessageConvert convert) {
    super(rankingRepository, eventHandledRepository, weightRepository, convert);
    this.repository = repository;
  }

  public void sum(List<String> values) {
    Map<Long, Long> map = process(values).stream()
        .filter(StockMetricsMessage.class::isInstance)  // BaseMessage 중 StockMetricsMessage만 선택
        .map(StockMetricsMessage.class::cast)
        .collect(groupingBy(StockMetricsMessage::productId, Collectors.summingLong(StockMetricsMessage::quantity)));

    // 파티션별로
    for (Entry<Long, Long> entry : map.entrySet()) {
      Long productId = entry.getKey();
      Long sum = entry.getValue();
      repository.upsertSales(productId, sum);
      increment(productId, weight().getSales(), sum);
    }
  }


  @Override
  public MetricsMethod method() {
    return MetricsMethod.SALES;
  }
}
