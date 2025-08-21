package com.loopers.application.payment;

import com.loopers.domain.payment.CardType;
import com.loopers.domain.payment.PaymentTool;
import java.math.BigInteger;

public record PaymentGatewayCommand(
    String userId,
    String orderId,
    PaymentTool paymentTool,
    CardType cardType,
    String cardNo,
    BigInteger amount) {

  private PaymentGatewayCommand(PaymentCommand command) {
    this(command.userId(),
        command.orderNumber(),
        PaymentTool.valueOf(command.paymentTool().name()),
        CardType.valueOf(command.cardType().name()),
        command.cardNo(),
        command.payment());
  }

  public static PaymentGatewayCommand of(PaymentCommand command) {
    return new PaymentGatewayCommand(command);
  }
}
