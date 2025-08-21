package com.loopers.application.payment;

import com.loopers.domain.payment.CardType;
import java.math.BigInteger;
import lombok.Builder;

@Builder
public record PaymentCommand(
    String userId,
    String orderNumber,
    String pgUserId,
    // 어떤 방법으로 결제 할지?
    PaymentTool paymentTool,

    CardType cardType,
    String cardNo,

    BigInteger payment,
    String description
) {

  public PaymentCommand(String userId,
                        String orderNumber,
                        String pgUserId,
                        String tool,
                        String cardType,
                        String cardNo,
                        BigInteger payment,
                        String description) {
    this(userId, orderNumber, pgUserId, PaymentTool.valueOf(tool), CardType.valueOf(cardType), cardNo, payment, description);
  }

  public PaymentCommand(String userId,
                        String orderNumber,
                        String pgUserId,
                        String tool,
                        BigInteger payment,
                        String description) {
    this(userId, orderNumber, pgUserId, PaymentTool.valueOf(tool), null, null, payment, description);
  }


  enum PaymentTool {
    CARD,
    POINT,
  }

  String tool() {
    return paymentTool().name();
  }
}
