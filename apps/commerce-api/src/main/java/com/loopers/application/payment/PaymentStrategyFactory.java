package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentTool;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {
  private final Map<PaymentTool, PaymentStrategy> strategyMap = new HashMap<>();

  public PaymentStrategyFactory(List<PaymentStrategy> strategies) {
    // Bean으로 등록된 모든 전략을 List로 받아서 Map으로 변환
    for (PaymentStrategy strategy : strategies) {
      strategyMap.put(strategy.getType(), strategy);
    }
  }

  public PaymentStrategy getStrategy(PaymentTool type) {
    return strategyMap.get(type);
  }
}
