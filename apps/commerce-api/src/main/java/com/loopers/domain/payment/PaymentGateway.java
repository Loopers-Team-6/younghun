package com.loopers.domain.payment;

import com.loopers.application.payment.PaymentRequest;
import com.loopers.application.payment.PaymentResponse;
import com.loopers.interfaces.api.ApiResponse;

public interface PaymentGateway {

  ApiResponse<PaymentResponse> action(String userId, PaymentRequest paymentRequest);
}
