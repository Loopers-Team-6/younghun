package com.loopers.domain.point;

import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointRepository pointRepository;

  public PointModel get(String userId) {
    return pointRepository.get(userId);
  }

  public PointModel charge(String userId, BigInteger point) {
    return pointRepository.charge(userId, point);
  }
}
