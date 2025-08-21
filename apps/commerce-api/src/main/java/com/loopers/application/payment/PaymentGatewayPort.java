package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentGateway;
import com.loopers.domain.payment.PaymentRepository;
import com.loopers.domain.payment.PaymentStatus;
import com.loopers.domain.payment.TransactionStatusResponse;
import com.loopers.infrastructure.payment.OrderResponse;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentGatewayPort {
  private final PaymentGateway paymentGateway;
  private final PaymentRepository paymentRepository;

  @Retry(name = "pg-payment", fallbackMethod = "handlePaymentFailure")
  @CircuitBreaker(name = "pg-payment", fallbackMethod = "handlePaymentFailure")
  public PaymentResponse send(PaymentGatewayCommand command) {
    try {
      return paymentGateway.send(
          command.userId(),
          new PaymentRequest(command, "http://localhost:8080/api/v1/payment/callback")
      ).data();
    } catch (Exception ex) {
      // 실제 예외는 fallback으로 넘어가므로 여기서는 선택적 로깅
      log.error("send() 예외 발생", ex);
      throw ex;
    }
  }

  @Retry(name = "get-payment", fallbackMethod = "handlePaymentGetFailure")
  @CircuitBreaker(name = "get-payment", fallbackMethod = "handlePaymentGetFailure")
  public OrderResponse get(String orderId) {
    return paymentGateway.get(orderId).data();
  }

  private PaymentResponse handlePaymentFailure(PaymentGatewayCommand command, Throwable ex) {
    log.error("PG 요청 실패: orderId={}, userId={}", command.orderId(), command.userId(), ex);
    // DB 상태 업데이트
    paymentRepository.updateStatus(command.orderId(), PaymentStatus.valueOf(TransactionStatusResponse.FAILED.name()));

    return new PaymentResponse(null, TransactionStatusResponse.FAILED, "결제 실패:" + ex.getMessage());
  }

  private OrderResponse handlePaymentGetFailure(Throwable ex) {
    throw new CoreException(ErrorType.INTERNAL_ERROR, ex.getMessage());
  }

  private OrderResponse handlePaymentFailureByOrder(
      String userId, String orderNumber, Throwable ex) {
    throw new CoreException(ErrorType.INTERNAL_ERROR, ex.getMessage());
  }


}
