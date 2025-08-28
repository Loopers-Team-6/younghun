package com.loopers.domain.coupon;

import java.util.Optional;

public interface CouponRepository {
  Optional<CouponModel> get(Long couponId);
  void used(Long couponId);

}
