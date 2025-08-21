package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentGateway;
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

  @Retry(name = "pg-payment", fallbackMethod = "handlePaymentFailure")
  @CircuitBreaker(name = "pg-payment", fallbackMethod = "handlePaymentFailure")
  public PaymentResponse send(PaymentGatewayCommand command) {
    return paymentGateway.send(command.userId(),
            new PaymentRequest(command, "http://localhost:8080/api/v1/payment/callback"))
        .data();

  }

  @Retry(name = "get-payment", fallbackMethod = "handlePaymentGetFailure")
  @CircuitBreaker(name = "get-payment", fallbackMethod = "handlePaymentGetFailure")
  public OrderResponse get(String orderId) {
    return paymentGateway.get(orderId).data();
  }

  @Retry(name = "get-payment", fallbackMethod = "handlePaymentFailureByOrder")
  @CircuitBreaker(name = "get-payment", fallbackMethod = "handlePaymentFailureByOrder")
  public OrderResponse get(String userId, String orderNumber) {
    return paymentGateway.get(userId, orderNumber).data();
  }


  private PaymentResponse handlePaymentFailure(
      PaymentGatewayCommand command, Throwable ex) {
    return new PaymentResponse(null, TransactionStatusResponse.FAILED, ex.getMessage());
  }

  private OrderResponse handlePaymentGetFailure(Throwable ex) {
    throw new CoreException(ErrorType.INTERNAL_ERROR, ex.getMessage());
  }

  private OrderResponse handlePaymentFailureByOrder(
      String userId, String orderNumber, Throwable ex) {
    throw new CoreException(ErrorType.INTERNAL_ERROR, ex.getMessage());
  }


}
