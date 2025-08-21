package com.loopers.domain.payment;

import com.loopers.application.payment.PaymentRequest;
import com.loopers.application.payment.PaymentResponse;
import com.loopers.infrastructure.payment.OrderResponse;
import com.loopers.interfaces.api.ApiResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentGateway {

  ApiResponse<PaymentResponse> send(String userId, PaymentRequest paymentRequest);

  ApiResponse<OrderResponse> get(String userId, String orderId);
  ApiResponse<OrderResponse> get(String orderId);
}
