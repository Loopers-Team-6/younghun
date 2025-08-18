package com.loopers.infrastructure.payment;

import com.loopers.application.payment.gateway.PaymentGateway;
import com.loopers.application.payment.gateway.PaymentRequest;
import com.loopers.application.payment.gateway.PaymentResponse;
import com.loopers.interfaces.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "payment-gateway", url = "localhost:8082/api/v1/payments")
public interface PaymentGatewayClient extends PaymentGateway {


  @PostMapping()
  ApiResponse<PaymentResponse> action(@RequestHeader("X-USER-ID") String userId,
                                      @RequestBody PaymentRequest paymentRequest);
}
