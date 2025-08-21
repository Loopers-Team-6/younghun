package com.loopers.application.payment;

public record PaymentRequest(
    String orderId,
    PaymentTool paymentTool,
    String cardType,
    String cardNo,
    Long amount,
    String callbackUrl
) {
  public PaymentRequest(PaymentGatewayCommand command, String callbackUrl) {
    this(command.orderId(), PaymentTool.valueOf(command.paymentTool().name()),
        "KB",
        "1234-1234-1234-1234",
        command.amount().longValue(), callbackUrl);
  }

  enum PaymentTool {
    CARD,
    POINT,
  }
}

