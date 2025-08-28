package com.loopers.infrastructure.payment;

import com.loopers.application.payment.PaymentPublisher;
import com.loopers.data_platform.PlatformSendEvent;
import com.loopers.domain.payment.PaymentOrderConfirmCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCorePublisher implements PaymentPublisher {
  private final ApplicationEventPublisher publisher;

  public void publish(String orderNumber) {
    publisher.publishEvent(new PaymentOrderConfirmCommand(orderNumber));
  }

  public void publish(Long paymentId, String result) {
    log.info("결제 정보에서 데이터 플랫폼으로 데이터를 전송합니다.");
    publisher.publishEvent(new PlatformSendEvent(
        "결제", paymentId, result
    ));
  }

}
