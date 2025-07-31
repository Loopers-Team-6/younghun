package com.loopers.infrastructure.order;

import com.loopers.domain.order.OrderModel;
import com.loopers.domain.order.OrderRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
  private final OrderJpaRepository repository;


  @Override
  public OrderModel save(OrderModel orderModel) {
    return repository.save(orderModel);
  }

  @Override
  public OrderModel ofOrderNumber(String orderNumber) {
    return repository.findByOrderNumber(orderNumber).orElseThrow(
        () -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 주문번호입니다.")
    );
  }
}
