package com.loopers.application.order;

import com.loopers.domain.coupon.CouponModel;
import com.loopers.domain.coupon.CouponRepository;
import com.loopers.domain.coupon.issued.IssuedCoupon;
import com.loopers.domain.coupon.issued.IssuedCouponRepository;
import com.loopers.domain.order.OrderCouponRegisterCommand;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponProcessor {
  private final CouponRepository repository;
  private final IssuedCouponRepository issuedCouponRepository;

  public BigInteger discount(BigInteger currentOrderPrice, Long couponId) {
    // 쿠폰이 적용되지 않는 경우
    if (couponId == null) {
      return BigInteger.ZERO;
    }

    CouponModel couponModel = repository.get(couponId)
        .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "해당하는 쿠폰은 존재하지 않습니다."));

    return couponModel.calculate(currentOrderPrice);
  }


  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void register(OrderCouponRegisterCommand command) {
    issuedCouponRepository.register(new IssuedCoupon(command.userId(), command.orderId(), command.couponId()));
    repository.used(command.couponId());
  }
}
