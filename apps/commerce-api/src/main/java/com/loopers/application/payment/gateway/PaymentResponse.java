package com.loopers.application.payment.gateway;

public record PaymentResponse(
    String transactionKey,
    TransactionStatusResponse statusResponse,
    String response
) {
}
