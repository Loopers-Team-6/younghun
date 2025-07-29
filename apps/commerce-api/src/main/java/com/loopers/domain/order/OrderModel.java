package com.loopers.domain.order;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "order")
@Getter
public class OrderModel extends BaseEntity {

  @Column(length = 25, unique = true)
  private String orderNumber;

  private Long memberId;

  private BigInteger totalPrice;

  @Column(length = 200)
  private String address;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(columnDefinition = "TEXT")
  private String memo;
}

