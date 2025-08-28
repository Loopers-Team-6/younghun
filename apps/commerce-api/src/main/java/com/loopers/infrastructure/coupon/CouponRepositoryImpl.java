package com.loopers.infrastructure.coupon;

import com.loopers.domain.coupon.CouponModel;
import com.loopers.domain.coupon.CouponRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {
  private final CouponJpaRepository repository;

  @Override
  public Optional<CouponModel> get(Long couponId) {
    return repository.findById(couponId);
  }

  @Override
  public void used(Long couponId) {
    repository.decreaseCouponCount(couponId);
  }

}
