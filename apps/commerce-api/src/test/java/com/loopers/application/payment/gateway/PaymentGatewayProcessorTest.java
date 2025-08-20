package com.loopers.application.payment.gateway;

import com.loopers.application.payment.CardType;
import com.loopers.application.payment.PaymentGatewayCommand;
import com.loopers.application.payment.PaymentGatewayProcessor;
import com.loopers.application.payment.PaymentRequest;
import com.loopers.application.payment.PaymentResponse;
import com.loopers.application.payment.TransactionStatusResponse;
import com.loopers.domain.payment.PaymentGateway;
import com.loopers.interfaces.api.ApiResponse;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class PaymentGatewayProcessorTest {

  @MockitoBean
  private PaymentGatewayProcessor paymentGatewayProcessor;

  private PaymentGateway sucessPaymentGateway;
  private PaymentGateway failPaymentGateway;
  private PaymentGateway pendingPaymentGateway;

  @BeforeEach
  void init() {
    sucessPaymentGateway = (userId, request) -> ApiResponse.success(new PaymentResponse("test1", TransactionStatusResponse.SUCCESS, "성공함"));
    failPaymentGateway = (userId, request) -> ApiResponse.success(new PaymentResponse("test2", TransactionStatusResponse.FAILED, "실패함"));
    pendingPaymentGateway = (userId, request) -> ApiResponse.success(new PaymentResponse("test3", TransactionStatusResponse.PENDING, "대기 중임"));
  }


  @DisplayName("PG사에 요청을 보냈을때, 성공 하는 경우")
  @Test
  void returnSuccess_whenSendPaymentRequest() {
    //given
    String userId = "userId";
    String orderId = "ORD-0001";
    CardType cardType = CardType.KB;
    String cardNo = "1234";
    BigInteger amount = BigInteger.valueOf(10000);
    PaymentGatewayCommand command = new PaymentGatewayCommand(userId, orderId, cardType, cardNo, amount);
     ApiResponse<PaymentResponse> callback = sucessPaymentGateway.action(userId, new PaymentRequest(orderId, cardType, cardNo, amount.longValue(), "callback"));
    //when

    CompletableFuture<ApiResponse<PaymentResponse>> response = paymentGatewayProcessor.send(command);
    //then
  }

  @DisplayName("PG사에 요청을 보냈지만, 실패하는 경우")
  @Test
  void returnFail_whenSendPaymentRequest() {
    //given
    String userId = "userId";
    String orderId = "ORD-0001";
    CardType cardType = CardType.KB;
    String cardNo = "1234";
    BigInteger amount = BigInteger.valueOf(500);
    PaymentGatewayCommand command = new PaymentGatewayCommand(userId, orderId, cardType, cardNo, amount);
    ApiResponse<PaymentResponse> callback = failPaymentGateway.action(userId, new PaymentRequest(orderId, cardType, cardNo, amount.longValue(), "callback"));
    //when
    CompletableFuture<ApiResponse<PaymentResponse>> response = paymentGatewayProcessor.send(command);
    //then

  }
}
