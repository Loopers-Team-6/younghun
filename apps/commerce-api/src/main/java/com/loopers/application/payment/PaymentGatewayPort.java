package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentGateway;
import com.loopers.domain.payment.TransactionStatusResponse;
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
    return paymentGateway.action(command.userId(),
            new PaymentRequest(command, "http://localhost:8080/api/v1/payment/callback"))
        .data();

  }


  private PaymentResponse handlePaymentFailure(
      PaymentGatewayCommand command, Throwable ex) {
    return new PaymentResponse(null, TransactionStatusResponse.FAILED, ex.getMessage());
  }
}
