package com.loopers.application.payment.command;

import com.loopers.application.payment.gateway.CardType;
import java.math.BigInteger;

public record PaymentCommand(
    String userId,
    String orderNumber,

    // 어떤 방법으로 결제 할지?
    CardType cardType,
    String cardNo,

    BigInteger payment,
    String description
) {
}
