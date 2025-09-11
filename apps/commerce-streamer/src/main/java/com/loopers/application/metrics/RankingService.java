package com.loopers.application.metrics;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;

@Service
public class RankingService {
  private final MetricsStrategyFactory factory;

  public RankingService(MetricsStrategyFactory factory) {
    this.factory = factory;
  }

  public void metics(Map<String, List<String>> collected) {
    for (Entry<String, List<String>> entry : collected.entrySet()) {
      String topic = entry.getKey();
      List<String> values = entry.getValue();
      factory.getStrategy(MetricsMethod.find(topic)).sum(values);
    }

  }
}
