package com.loopers.domain.payment;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payment")
@Getter
public class PaymentModel extends BaseEntity {

  @Column(nullable = false, length = 25)
  private String orderNumber;

  @Column(nullable = false, length = 25)
  private String memberId;

  @Column(nullable = false)
  private BigInteger paymentAmount;

  @Column(columnDefinition = "TEXT")
  private String description;
}
