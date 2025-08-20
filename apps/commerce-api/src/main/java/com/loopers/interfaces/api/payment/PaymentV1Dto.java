package com.loopers.interfaces.api.payment;

import com.loopers.application.payment.PaymentCallBackCommand;
import com.loopers.application.payment.PaymentCommand;
import com.loopers.application.payment.PaymentInfo;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

public class PaymentV1Dto {

  public record CreateResponse(
      String userId,
      String orderNumber,
      BigInteger orderPrice, // 주문 금액
      BigInteger paymentPrice, //결제 금액
      String description
  ) {

    public CreateResponse(PaymentInfo payment) {
      this(payment.userId(),
          payment.orderNumber(),
          payment.orderPrice(),
          payment.paymentPrice(),
          payment.description());
    }
  }

  public record Create(
      @NotNull
      String orderNumber,

      String transactionKey,
      @NotNull
      PaymentTool paymentTool,

      @NotNull
      BigInteger payment,
      String description

  ) {
    public PaymentCommand toCommand(String userId) {
      return new PaymentCommand(
          userId,
          orderNumber,
          transactionKey,
          paymentTool.name(),
          payment,
          description
      );
    }
  }

  public record Callback(
      @NotNull
      String transactionKey,
      @NotNull
      String orderId,
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
          amount,
          status.name(),
          reason
      );
    }

  }

  enum PaymentTool {
    CARD,
    POINT,
  }



  enum TransactionStatus {
    PENDING, FAIL, SUCCESS
  }
}
