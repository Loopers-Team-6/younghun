package com.loopers.application.payment;


public interface PaymentPublisher {

  void publish(String orderNumber);

  void publish(Long paymentId, String result);

  void send(String userId, String message);
  void fail(String userId, String failMessage);
}
