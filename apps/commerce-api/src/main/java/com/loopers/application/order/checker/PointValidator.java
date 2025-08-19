package com.loopers.application.order.checker;

import com.loopers.domain.point.PointModel;
import com.loopers.domain.point.PointRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointValidator {
  private final PointRepository pointRepository;


  public void usePointCheck(String userId, BigInteger usePoint) {
    PointModel pointModel = pointRepository.get(userId)
        .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "포인트가 존재하지 않습니다."));

    BigInteger hasPoint = pointModel.getPoint();

    if (hasPoint.compareTo(usePoint) < 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "사용할 포인트는 가지고 있는 포인트 보다 클 수 없습니다");
    }

  }
}
