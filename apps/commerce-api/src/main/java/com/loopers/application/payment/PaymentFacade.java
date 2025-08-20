package com.loopers.application.payment;


import com.loopers.domain.order.OrderModel;
import com.loopers.domain.order.OrderRepository;
import com.loopers.domain.order.orderItem.OrderItemModel;
import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
  private final PaymentRepository paymentRepository;
  private final OrderRepository orderRepository;
  private final PointUseHandler pointUseHandler;
  private final StockProcessor stockProcessor;

  private final PaymentGatewayProcessor gatewayProcessor;

  @Transactional
  public PaymentInfo payment(PaymentCommand command) {
    String orderNumber = command.orderNumber();
    OrderModel orderModel = orderRepository.ofOrderNumber(orderNumber);

    // 포인트 감소
    pointUseHandler.use(command.userId(), orderModel.getUsePoint());

    // 결제 처리
    PaymentModel payment = paymentRepository.save(PaymentModel.create()
        .userId(command.userId())
        .orderNumber(orderNumber)
        .description(command.description())
        .orderAmount(orderModel.getUsePoint().add(command.payment()))
        .paymentAmount(orderModel.getTotalPrice())
        .build());

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

    return PaymentInfo.builder()
        .userId(payment.getUserId())
        .orderNumber(payment.getOrderNumber())
        .orderPrice(payment.getOrderAmount())
        .paymentPrice(payment.getPaymentAmount())
        .description(payment.getDescription())
        .build();
  }
}
