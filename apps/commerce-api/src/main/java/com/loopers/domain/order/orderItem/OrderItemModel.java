package com.loopers.domain.order.orderItem;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
@Getter
public class OrderItemModel extends BaseEntity {

  private Long orderId;
  private Long productId;
  private Long quantity;
  private BigInteger unitPrice;

  @Builder
  public OrderItemModel(Long orderId, Long productId, Long quantity, BigInteger unitPrice) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public void plusQuantity(Long quantity) {
    this.quantity += quantity;
  }
}
