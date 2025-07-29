package com.loopers.domain.order.history;


import com.loopers.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "order_history")
@Getter
public class OrderHistoryModel extends BaseEntity {
  @Column(nullable = false)
  private Long orderId;
  @Column(nullable = false, length = 25)
  private String orderNumber;

  @Column(nullable = false)
  private String memberId;

  @Column(nullable = false, length = 25)
  private String status;

  private String address;

  @Column(columnDefinition = "TEXT")
  private String memo;
}
