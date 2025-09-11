package com.loopers.application.metrics;

import static java.util.stream.Collectors.groupingBy;

import com.loopers.domain.StockMetricsMessage;
import com.loopers.domain.event.EventHandledRepository;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.SalesMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConvert;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MetricsSalesStrategy extends MetricsStrategy {
  private final MessageConvert convert;
  private final EventHandledRepository eventHandledRepository;
  private final MetricsRepository repository;

  public MetricsSalesStrategy(MetricsRepository repository, RankingRepository rankingRepository,
                              EventHandledRepository eventHandledRepository,
                              MessageConvert convert) {
    super(rankingRepository, eventHandledRepository, convert);
    this.repository = repository;
    this.eventHandledRepository = eventHandledRepository;
    this.convert = convert;
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
      increment(productId, 0.7, sum);
    }
  }

  @Override
  public void process(String message) {
    Message convertMessage = convert.convert(message, Message.class);
    SalesMetricsMessage result = convert.convert(convertMessage.getPayload(), SalesMetricsMessage.class);
    repository.upsertSales(result.productId(), result.quantity());
    eventHandledRepository.save(convertMessage.getEventId());
    increment(result.productId(), 0.7, result.quantity());
  }

  @Override
  public MetricsMethod method() {
    return MetricsMethod.SALES;
  }
}
