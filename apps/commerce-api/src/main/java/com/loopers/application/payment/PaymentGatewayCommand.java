package com.loopers.application.payment;

import com.loopers.domain.payment.CardType;
import java.math.BigInteger;

public record PaymentGatewayCommand(
    String userId,
    String orderId,
    String transactionKey,
    CardType cardType,
    BigInteger amount) {

  private PaymentGatewayCommand(PaymentCommand command) {
    this(command.userId(),
        command.orderNumber(),
        command.transactionKey(),
        command.cardType(),
        command.payment());
  }

  public static PaymentGatewayCommand of(PaymentCommand command) {
    return new PaymentGatewayCommand(command);
  }
}
