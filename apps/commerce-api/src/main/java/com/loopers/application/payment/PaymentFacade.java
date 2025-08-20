package com.loopers.application.payment;


import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentTool;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

  private final PaymentProcessor paymentProcessor;
  private final PaymentHistoryProcessor paymentHistoryProcessor;
  private final PaymentStrategyFactory paymentFactory;

  public PaymentInfo payment(PaymentCommand command) {

    PaymentStrategy strategy = paymentFactory.getStrategy(PaymentTool.valueOf(command.tool()));
    PaymentModel payment = strategy.process(command);

    return PaymentInfo.builder()
        .userId(payment.getUserId())
        .orderNumber(payment.getOrderNumber())
        .orderPrice(payment.getOrderAmount())
        .paymentPrice(payment.getPaymentAmount())
        .description(payment.getDescription())
        .build();
  }

  @Transactional
  public void callback(PaymentCallBackCommand command) {
    PaymentModel paymentModel = paymentProcessor.get(command.orderId());
    paymentModel.changeStatus(command.paymentStatus());

    paymentHistoryProcessor.add(paymentModel, command.reason());
  }
}
