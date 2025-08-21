package com.loopers.application.payment;

import com.loopers.domain.order.OrderModel;
import com.loopers.domain.order.OrderRepository;
import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentTool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCardStrategy implements PaymentStrategy {
  private final PaymentGatewayPort gatewayProcessor;
  private final PaymentProcessor paymentProcessor;
  private final OrderRepository orderRepository;

  private final PaymentHistoryProcessor paymentHistoryProcessor;

  @Override
  public PaymentModel process(PaymentCommand command) {
    OrderModel orderModel = orderRepository.ofOrderNumber(command.orderNumber());


    // PG사 요청
    PaymentResponse response = gatewayProcessor.send(PaymentGatewayCommand.of(command));

    PaymentModel payment = paymentProcessor.create(new PaymentProcessorVo(
        command.userId(),
        command.orderNumber(),
        command.description(),
        response.transactionKey(),
        getType().name(),
        command.payment(),
        orderModel.getTotalPrice()
    ));

    paymentHistoryProcessor.add(payment, "결제 대기중입니다.");
    return payment;
  }

  @Override
  public PaymentTool getType() {
    return PaymentTool.CARD;
  }
}
