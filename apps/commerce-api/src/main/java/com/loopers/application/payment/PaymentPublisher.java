package com.loopers.application.payment;


public interface PaymentPublisher {

  void publish(String orderNumber);
}
