package com.loopers.infrastructure.order;

import com.loopers.domain.order.OrderModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderJpaRepository extends JpaRepository<OrderModel, Long> {
  Optional<OrderModel> findById(Long id);
  @Query("SELECT o FROM OrderModel o WHERE o.orderNumber.number = :orderNumber")
  Optional<OrderModel> findByOrderNumber(String orderNumber);
}
