package com.loopers.application.payment.gateway;

import com.loopers.interfaces.api.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentGatewayProcessor {
  private final PaymentGateway paymentGateway;

  @CircuitBreaker(name = "pg-payment", fallbackMethod = "handlePaymentFailure")
  @TimeLimiter(name = "pg-payment")
  public CompletableFuture<ApiResponse<PaymentResponse>> send(PaymentGatewayCommand command) {
    return CompletableFuture.supplyAsync(() -> paymentGateway.action(command.userId(),
        new PaymentRequest(command, "http://localhost:8080/payment/callback")));
  }


  public CompletableFuture<ApiResponse<PaymentResponse>> handlePaymentFailure(PaymentGatewayCommand command,
                                                                              Throwable t) {
    log.info("Fallback executed: {}", t.getMessage());

    // 사용자에게는 즉시 실패 응답 전달
    ApiResponse<PaymentResponse> fallbackResponse = ApiResponse.success(
        new PaymentResponse("asa", TransactionStatusResponse.FAILED, "실패"));
    return CompletableFuture.completedFuture(fallbackResponse);
  }
}
