package com.loopers.application.payment.gateway;

import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayProcessor {
  private final PaymentGateway paymentGateway;

  public ApiResponse<PaymentResponse> send(PaymentGatewayCommand command) {
    return paymentGateway.action(command.userId(), new PaymentRequest(command, "http://localhost:8080/payment/callback"));
  }
}
