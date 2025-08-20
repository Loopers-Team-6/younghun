package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProcessor {
  private final PaymentRepository paymentRepository;

  public PaymentModel create(PaymentProcessorVo vo) {
    return paymentRepository.save(PaymentModel.create()
        .userId(vo.userId())
        .orderNumber(vo.orderNumber())
        .description(vo.description())
        .orderAmount(vo.payment())
        .paymentAmount(vo.totalPrice())
        .build());

  }
}
