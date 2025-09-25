package com.loopers.application.rank;

import com.loopers.infrastructure.mv.DateType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DateStrategyFactory{

  private final Map<DateType, DateStrategy> strategyMap = new HashMap<>();

  public DateStrategyFactory(List<DateStrategy> strategies) {
    // Bean으로 등록된 모든 전략을 List로 받아서 Map으로 변환
    for (DateStrategy strategy : strategies) {
      strategyMap.put(strategy.getType(), strategy);
    }
  }

  public DateStrategy getStrategy(String type) {
    return strategyMap.get(DateType.valueOf(type));
  }
}
