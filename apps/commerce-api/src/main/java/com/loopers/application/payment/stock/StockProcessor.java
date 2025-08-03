package com.loopers.application.payment.stock;

import com.loopers.domain.catalog.product.stock.StockModel;
import com.loopers.domain.catalog.product.stock.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockProcessor {
  private final StockRepository stockRepository;

  @Transactional
  public void decreaseStocks(List<StockDecreaseCommand> orderItems) {
    for (StockDecreaseCommand orderItem : orderItems) {
      Long productId = orderItem.productId();
      StockModel stockModel = stockRepository.get(productId);
      stockModel.decrease(orderItem.quantity());
    }
  }
}
