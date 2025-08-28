package com.loopers.infrastructure.payment;

import com.loopers.application.payment.PaymentPublisher;
import com.loopers.domain.payment.PaymentOrderConfirmCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCorePublisher implements PaymentPublisher {
  private final ApplicationEventPublisher publisher;

  public void publish(String orderNumber) {
    publisher.publishEvent(new PaymentOrderConfirmCommand(orderNumber));
  }
}
