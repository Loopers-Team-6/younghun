package com.loopers.domain.payment;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Builder(builderMethodName = "create")
  public PaymentModel(String orderNumber, String memberId, BigInteger paymentAmount, String description) {
    if(orderNumber == null) {
      throw new CoreException(ErrorType.NOT_FOUND,"결제 정보에는 주문 번호가 존재해야합니다.");
    }

    if(memberId == null) {
      throw new CoreException(ErrorType.NOT_FOUND,"결제 정보에는 결제가 존재해야합니다.");
    }

    this.orderNumber = orderNumber;
    this.memberId = memberId;
    this.paymentAmount = paymentAmount;
    this.description = description;

    if(paymentAmount == null) {
      this.paymentAmount = BigInteger.ZERO;
    }
  }
}
