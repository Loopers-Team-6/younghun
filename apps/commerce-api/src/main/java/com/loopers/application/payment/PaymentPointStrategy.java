package com.loopers.application.payment;

import com.loopers.domain.order.OrderModel;
import com.loopers.domain.order.OrderRepository;
import com.loopers.domain.order.orderItem.OrderItemModel;
import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentTool;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPointStrategy implements PaymentStrategy {
  private final PointUseHandler pointUseHandler;
  private final PaymentProcessor paymentProcessor;
  private final StockProcessor stockProcessor;
  private final OrderRepository orderRepository;

  private final PaymentHistoryProcessor paymentHistoryProcessor;


  @Override
  @Transactional
  public PaymentModel process(PaymentCommand command) {
    OrderModel orderModel = orderRepository.ofOrderNumber(command.orderNumber());

    // 포인트 감소
    pointUseHandler.use(command.userId(), command.payment());

    // 결제 처리
    PaymentModel payment = paymentProcessor.create(new PaymentProcessorVo(
        command.userId(), command.orderNumber(), command.description(),
        null,
        getType().name(),
        command.payment(),
        orderModel.getTotalPrice()
    ));

    // 재고 차감
    for (OrderItemModel orderItem : orderModel.getOrderItems()) {
      Long productId = orderItem.getProductId();
      Long quantity = orderItem.getQuantity();
      stockProcessor.decreaseStock(productId, quantity);
    }

    orderModel.done();
    payment.done();

    paymentHistoryProcessor.add(payment, null);

    return payment;
  }

  @Override
  public PaymentTool getType() {
    return PaymentTool.POINT;
  }
}
