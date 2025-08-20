package com.loopers.application.payment;

import com.loopers.domain.payment.CardType;

public record PaymentRequest(
    String orderId,
    CardType cardType,
    Long amount,
    String callbackUrl
) {
  public PaymentRequest(PaymentGatewayCommand command, String callbackUrl) {
    this(command.orderId(), command.cardType(), command.amount().longValue(), callbackUrl);
  }
}

