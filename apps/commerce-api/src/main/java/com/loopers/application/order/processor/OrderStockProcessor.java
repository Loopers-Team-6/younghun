package com.loopers.application.order.processor;

import com.loopers.domain.catalog.product.stock.StockModel;
import com.loopers.domain.catalog.product.stock.StockRepository;
import com.loopers.domain.order.orderItem.OrderItemModel;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStockProcessor {
  private final StockRepository stockRepository;

  public void check(List<OrderItemModel> orderItems) {
    for (OrderItemModel orderItem : orderItems) {
      StockModel stockModel = stockRepository.get(orderItem.getProductId());
      Long hasStock = stockModel.stock();
      // 주문 수량
      Long orderItemQuantity = orderItem.getQuantity();

      if (hasStock < orderItemQuantity) {
        throw new CoreException(ErrorType.BAD_REQUEST, "주문 수량이 재고보다 많습니다.");
      }
    }
  }
}
