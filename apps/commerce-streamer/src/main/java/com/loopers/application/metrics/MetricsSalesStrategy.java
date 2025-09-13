package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.StockMetricsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.rank.RankRepository;
import com.loopers.domain.weight.WeightRepository;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MetricsSalesStrategy extends MetricsStrategy {
  private final MetricsPublisher publisher;

  public MetricsSalesStrategy(RankingRepository rankingRepository,
                              EventHandledRepository eventHandledRepository,
                              WeightRepository weightRepository,
                              MessageConvert convert, RankRepository rankRepository, MetricsPublisher publisher) {
    super(rankingRepository, eventHandledRepository, weightRepository, convert, rankRepository);
    this.publisher = publisher;
  }

  public void sum(List<String> values) {
    Map<Long, Long> map = process(values).stream()
        .filter(StockMetricsMessage.class::isInstance)  // BaseMessage 중 StockMetricsMessage만 선택
        .map(StockMetricsMessage.class::cast)
        .collect(groupingBy(StockMetricsMessage::productId, Collectors.summingLong(StockMetricsMessage::quantity)));

//    publisher.sales(map);
    increment(map, weight().getSales());
  }


  @Override
  public MetricsMethod method() {
    return MetricsMethod.SALES;
  }
}
