package com.loopers.infrastructure.payment;

import com.loopers.domain.payment.TransactionStatusResponse;
import java.util.List;

public record OrderResponse(
    String orderId,
    List<TransactionResponse> transactions
) {

  public record TransactionResponse(
      String transactionKey,
      TransactionStatusResponse status,
      String reason

  ) {
  }
}
