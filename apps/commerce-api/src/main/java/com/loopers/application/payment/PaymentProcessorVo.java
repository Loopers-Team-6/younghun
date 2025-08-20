package com.loopers.application.payment;

import java.math.BigInteger;


public record PaymentProcessorVo(
    String userId,
    String orderNumber,
    String paymentTool,
    String description,
    BigInteger payment,
    BigInteger totalPrice
) {
}
