package com.loopers.application.payment;


import com.loopers.application.payment.callback.PaymentCallBackCommand;
import com.loopers.application.payment.history.PaymentHistoryProcessor;
import com.loopers.domain.order.OrderModel;
import com.loopers.domain.order.OrderRepository;
import com.loopers.domain.order.orderItem.OrderItemModel;
import com.loopers.domain.payment.PaymentModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

  private final PaymentProcessor paymentProcessor;
  private final PaymentHistoryProcessor paymentHistoryProcessor;
  private final OrderRepository orderRepository;
  private final PointUseHandler pointUseHandler;
  private final StockProcessor stockProcessor;

  private final PaymentGatewayPort gatewayProcessor;

  @Transactional
  public PaymentInfo payment(PaymentCommand command) {
    String orderNumber = command.orderNumber();
    OrderModel orderModel = orderRepository.ofOrderNumber(orderNumber);

    // 포인트 감소
    pointUseHandler.use(command.userId(), orderModel.getUsePoint());

    // 결제 처리
    PaymentModel payment = paymentProcessor.create(new PaymentProcessorVo(
        command.userId(), orderNumber, command.description(),
        orderModel.getUsePoint().add(command.payment()),
        orderModel.getTotalPrice()
    ));

    // 재고 차감
    for (OrderItemModel orderItem : orderModel.getOrderItems()) {
      Long productId = orderItem.getProductId();
      Long quantity = orderItem.getQuantity();
      stockProcessor.decreaseStock(productId, quantity);
    }

    // PG사 요청
    gatewayProcessor.send(PaymentGatewayCommand.of(command));

    // 주문 완료
    orderModel.done();

    paymentHistoryProcessor.add(payment, null);

    return PaymentInfo.builder()
        .userId(payment.getUserId())
        .orderNumber(payment.getOrderNumber())
        .orderPrice(payment.getOrderAmount())
        .paymentPrice(payment.getPaymentAmount())
        .description(payment.getDescription())
        .build();
  }

  @Transactional
  public void callback(PaymentCallBackCommand command) {
    PaymentModel paymentModel = paymentProcessor.get(command.orderId());
    paymentModel.changeStatus(command.paymentStatus());

    paymentHistoryProcessor.add(paymentModel, command.reason());
  }
}
