package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentGateway;
import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentRepository;
import com.loopers.domain.payment.PaymentStatus;
import com.loopers.domain.payment.TransactionStatusResponse;
import com.loopers.infrastructure.payment.OrderResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
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

  @Retry(name = "get-payment", fallbackMethod = "handlePaymentGetFallback")
  @CircuitBreaker(name = "get-payment", fallbackMethod = "handlePaymentGetFallback")
  public OrderResponse get(String orderId) {
    return paymentGateway.get(orderId).data();
  }

  private PaymentResponse handlePaymentFailure(PaymentGatewayCommand command, Throwable ex) {
    log.error("PG 요청 실패: orderId={}, userId={}", command.orderId(), command.userId(), ex);
    // DB 상태 업데이트
    paymentRepository.updateStatus(command.orderId(), PaymentStatus.valueOf(TransactionStatusResponse.FAILED.name()));

    return new PaymentResponse(null, TransactionStatusResponse.FAILED, "결제 실패:" + ex.getMessage());
  }


  private OrderResponse handlePaymentGetFallback(
      String orderId, Throwable ex) {
    List<PaymentModel> payments = paymentRepository.getAll(orderId);

    if (payments.isEmpty()) {
      // DB에도 없으면 최소한 빈 상태 반환
      return new OrderResponse(orderId, List.of());
    }

    // DB 데이터를 OrderResponse로 변환
    List<OrderResponse.TransactionResponse> transactions = payments.stream()
        .map(p -> new OrderResponse.TransactionResponse(
            p.getTransactionId(),
            TransactionStatusResponse.valueOf(p.getStatus().name()),
            "fallback DB 상태"
        ))
        .toList();

    return new OrderResponse(orderId, transactions);
  }


}
