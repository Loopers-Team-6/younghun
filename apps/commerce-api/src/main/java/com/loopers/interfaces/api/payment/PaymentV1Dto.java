package com.loopers.interfaces.api.payment;

import com.loopers.application.payment.callback.PaymentCallBackCommand;
import jakarta.validation.constraints.NotNull;

public class PaymentV1Dto {

  /**
   * val transactionKey: String, val orderId: String, val cardType: CardType, val cardNo: String, val amount: Long, val status:
   * TransactionStatus, val reason: String?,
   */

  record Callback(
      @NotNull
      String transactionKey,
      @NotNull
      String orderId,
      @NotNull
      CardType cardType,
      @NotNull
      String cardNo,
      @NotNull
      Long amount,
      @NotNull
      TransactionStatus status,
      String reason
  ) {

    public PaymentCallBackCommand toCommand() {
      return new PaymentCallBackCommand(
          transactionKey,
          orderId,
          cardType.name(),
          cardNo,
          amount,
          status.name(),
          reason
      );
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
}
