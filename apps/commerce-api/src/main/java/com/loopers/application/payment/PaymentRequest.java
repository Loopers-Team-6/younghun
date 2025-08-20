package com.loopers.application.payment;

import com.loopers.domain.payment.CardType;

public record PaymentRequest(
    String orderId,
    CardType cardType,
    String cardNo,
    Long amount,
    String callbackUrl
) {
  public PaymentRequest(PaymentGatewayCommand command, String callbackUrl) {
    this(command.orderId(), command.cardType(), command.cardNo(), command.amount().longValue(), callbackUrl);
  }
}

