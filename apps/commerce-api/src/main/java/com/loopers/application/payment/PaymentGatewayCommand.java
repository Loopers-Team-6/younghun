package com.loopers.application.payment;

import java.math.BigInteger;

public record PaymentGatewayCommand(
    String userId,
    String orderId,
    CardType cardType,
    String cardNo,
    BigInteger amount) {

  private PaymentGatewayCommand(PaymentCommand command) {
    this(command.userId(),
        command.orderNumber(),
        command.cardType(),
        command.cardNo(),
        command.payment());
  }

  public static PaymentGatewayCommand of(PaymentCommand command) {
    return new PaymentGatewayCommand(command);
  }
}
