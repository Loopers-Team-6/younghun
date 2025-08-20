package com.loopers.application.payment;

public record PaymentResponse(
    String transactionKey,
    TransactionStatusResponse statusResponse,
    String response
) {
}
