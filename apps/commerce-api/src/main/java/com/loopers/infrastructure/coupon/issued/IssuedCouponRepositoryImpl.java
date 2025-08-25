package com.loopers.infrastructure.coupon.issued;

import com.loopers.domain.coupon.issued.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IssuedCouponRepositoryImpl implements IssuedCouponRepository {
  private IssuedCouponJpaRepository issuedCouponJpaRepository;
}
