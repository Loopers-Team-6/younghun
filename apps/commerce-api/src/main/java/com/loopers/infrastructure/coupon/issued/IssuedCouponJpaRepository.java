package com.loopers.infrastructure.coupon.issued;

import com.loopers.domain.coupon.issued.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponJpaRepository extends JpaRepository<IssuedCoupon, Long> {
}
