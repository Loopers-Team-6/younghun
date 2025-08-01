package com.loopers.domain.point;

import java.math.BigInteger;

public interface PointRepository {

  PointModel get(String userId);

  PointModel charge(String userId, BigInteger point);
}
