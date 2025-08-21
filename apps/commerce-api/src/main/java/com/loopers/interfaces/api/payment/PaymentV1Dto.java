package com.loopers.interfaces.api.payment;

import com.loopers.application.payment.PaymentCallBackCommand;
import com.loopers.application.payment.PaymentCommand;
import com.loopers.application.payment.PaymentInfo;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
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

      @NotNull
      PaymentTool paymentTool,
      Card cardInfo,
      @NotNull
      BigInteger payment,
      String description

  ) {

    public Create {
      if(PaymentTool.CARD == paymentTool && cardInfo == null) {
        throw new CoreException(ErrorType.BAD_REQUEST, "결제 타입이 카드인 경우에는 카드 정보를 입력해야 합니다.");
      }

    }

    public PaymentCommand toCommand(String userId) {
      return new PaymentCommand(
          userId,
          orderNumber,
          paymentTool.name(),
          cardInfo.cardType.name(),
          cardInfo.cardNo,
          payment,
          description
      );
    }

    public record Card(
        CardType cardType,
        String cardNo
    ) {

      enum CardType {
        SAMSUNG,
        KB,
        HYUNDAI,
      }

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
    PENDING, FAILED, SUCCESS
  }
}
