package com.loopers.application.payment;

import java.math.BigInteger;

public record PaymentCommand(
    String userId,
    String orderNumber,

    // 어떤 방법으로 결제 할지?
    String transactionKey,
    PaymentTool paymentTool,

    BigInteger payment,
    String description
) {

  public PaymentCommand(String userId, String orderNumber, String transactionKey, String tool, BigInteger payment,
                        String description) {
    this(userId, orderNumber, transactionKey, PaymentTool.valueOf(tool), payment, description);
  }

  enum PaymentTool {
    CARD,
    POINT,
  }

  String tool() {
    return paymentTool().name();
  }
}
