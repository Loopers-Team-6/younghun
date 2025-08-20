package com.loopers.application.payment;

import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentTool;

public interface PaymentStrategy {

  PaymentModel process(PaymentCommand command);

  PaymentTool getType();
}
