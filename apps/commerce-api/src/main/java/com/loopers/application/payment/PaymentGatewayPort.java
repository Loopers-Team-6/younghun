package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentGateway;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.support.error.ErrorType;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentGatewayPort {
  private final PaymentGateway paymentGateway;

  public void send(PaymentGatewayCommand command) {

    paymentGateway.action(command.userId(),
        new PaymentRequest(command, "http://localhost:8080/api/v1/payment/callback"));

  }


  private CompletableFuture<ApiResponse<Object>> handlePaymentFailure(
      PaymentGatewayCommand command, Throwable ex) {
    return CompletableFuture.completedFuture(
        ApiResponse.fail(ErrorType.INTERNAL_ERROR.getCode(), "PG 호출 실패: " + ex.getMessage()));
  }
}
