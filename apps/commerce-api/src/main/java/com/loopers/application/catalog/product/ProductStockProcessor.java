package com.loopers.application.catalog.product;

import com.loopers.domain.catalog.product.stock.StockDecreaseEvent;
import com.loopers.domain.order.orderItem.OrderItemModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductStockProcessor {
  private final StockProcessor stockProcessor;


  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void decrease(StockDecreaseEvent event) {
    for (OrderItemModel orderItem : event.orderItems()) {
      Long productId = orderItem.getProductId();
      Long quantity = orderItem.getQuantity();
      stockProcessor.decreaseStock(productId, quantity);
    }
  }
}
