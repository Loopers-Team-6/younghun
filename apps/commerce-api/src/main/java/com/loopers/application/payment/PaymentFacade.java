package com.loopers.application.payment;


import com.loopers.application.payment.stock.StockDecreaseCommand;
import com.loopers.application.payment.stock.StockProcessor;
import com.loopers.domain.order.OrderModel;
import com.loopers.domain.order.OrderRepository;
import com.loopers.domain.payment.PaymentModel;
import com.loopers.domain.payment.PaymentRepository;
import com.loopers.domain.point.PointModel;
import com.loopers.domain.point.PointRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
  private final PaymentRepository paymentRepository;
  private final OrderRepository orderRepository;
  private final PointRepository pointRepository;

  private final StockProcessor stockProcessor;

  @Transactional
  public PaymentInfo payment(PaymentCommand command) {
    String orderNumber = command.orderNumber();
    OrderModel orderModel = orderRepository.ofOrderNumber(orderNumber);

    // 포인트 감소
    PointModel hasPoint = pointRepository.get(command.userId()).orElseThrow(
        () -> new CoreException(ErrorType.BAD_REQUEST, "사용할 수 있는 포인트가 존재하지 않습니다.")
    );
    hasPoint.use(command.payment());

    PaymentModel paymentModel = PaymentModel.create()
        .userId(command.userId())
        .orderNumber(orderNumber)
        .description(command.description())
        .orderAmount(command.payment())
        .paymentAmount(orderModel.getTotalPrice())
        .build();

    // 결제 처리
    PaymentModel payment = paymentRepository.save(paymentModel);

    // 재고 차감
    stockProcessor.decreaseStocks(orderModel.getOrderItems()
        .stream()
        .map(o -> new StockDecreaseCommand(o.getProductId(), o.getQuantity()))
        .collect(Collectors.toList())
    );

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
