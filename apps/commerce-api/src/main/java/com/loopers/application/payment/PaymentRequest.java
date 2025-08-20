package com.loopers.application.payment;

public record PaymentRequest(
    String orderId,
    PaymentTool paymentTool,
    Long amount,
    String callbackUrl
) {
  public PaymentRequest(PaymentGatewayCommand command, String callbackUrl) {
    this(command.orderId(), PaymentTool.valueOf(command.paymentTool().name()), command.amount().longValue(), callbackUrl);
  }

  enum PaymentTool {
    CARD,
    POINT,
  }
}

