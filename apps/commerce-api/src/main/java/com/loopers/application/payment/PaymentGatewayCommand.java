package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentTool;
import java.math.BigInteger;

public record PaymentGatewayCommand(
    String userId,
    String orderId,
    PaymentTool paymentTool,
    String transactionKey,
    BigInteger amount) {

  private PaymentGatewayCommand(PaymentCommand command) {
    this(command.userId(),
        command.orderNumber(),
        PaymentTool.valueOf(command.paymentTool().name()),
        command.transactionKey(),
        command.payment());
  }

  public static PaymentGatewayCommand of(PaymentCommand command) {
    return new PaymentGatewayCommand(command);
  }
}
