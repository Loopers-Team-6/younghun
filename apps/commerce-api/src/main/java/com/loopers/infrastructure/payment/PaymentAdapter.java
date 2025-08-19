package com.loopers.infrastructure.payment;

import com.loopers.application.payment.gateway.PaymentGateway;
import com.loopers.application.payment.gateway.PaymentRequest;
import com.loopers.application.payment.gateway.PaymentResponse;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentGateway {
  private final PaymentGatewayClient client;

  @Override
  public ApiResponse<PaymentResponse> action(String userId, PaymentRequest paymentRequest) {
    return client.action(userId, paymentRequest);
  }
}
