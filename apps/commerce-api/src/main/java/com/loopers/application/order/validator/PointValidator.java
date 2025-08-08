package com.loopers.application.order.validator;

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

  public void check(String userId, BigInteger orderPrice) {
    PointModel pointModel = pointRepository.get(userId).orElseThrow(() ->
        new CoreException(ErrorType.NOT_FOUND, "포인트가 존재하지 않습니다.")
    );

    BigInteger hasPoint = pointModel.getPoint();

    if (hasPoint.compareTo(orderPrice) < 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "포인트가 부족합니다.");
    }

  }
}
