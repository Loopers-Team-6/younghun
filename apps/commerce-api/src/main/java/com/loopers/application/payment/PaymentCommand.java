package com.loopers.application.payment;

import com.loopers.domain.payment.CardType;
import java.math.BigInteger;

public record PaymentCommand(
    String userId,
    String orderNumber,

    // 어떤 방법으로 결제 할지?
    String transactionKey,
    CardType cardType,
    String cardNo,

    BigInteger payment,
    String description
) {
}
