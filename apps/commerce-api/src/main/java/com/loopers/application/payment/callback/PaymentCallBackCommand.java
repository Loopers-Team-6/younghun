package com.loopers.application.payment.callback;

public record PaymentCallBackCommand(
    String transactionKey,
    String orderId,
    CardType cardType,
    String cardNo,
    Long amount,
    TransactionStatus status,
    String reason
) {

  public PaymentCallBackCommand(String transactionKey, String orderId, String cardType, String cardNo, Long amount,
                                String status, String reason) {
    this(transactionKey, orderId, CardType.valueOf(cardType), cardNo, amount, TransactionStatus.valueOf(status), reason);
  }

  public String paymentStatus() {
    return status.name();
  }

  public String card() {
    return cardType.name();
  }
}

enum CardType {
  SAMSUNG,
  KB,
  HYUNDAI,
}

enum TransactionStatus {
  PENDING, FAIL, SUCCESS
}
