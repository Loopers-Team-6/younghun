package com.loopers.application.payment.gateway;

import com.loopers.interfaces.api.ApiResponse;

public interface PaymentGateway {

  ApiResponse<PaymentResponse> action(String userId, PaymentRequest paymentRequest);
}
