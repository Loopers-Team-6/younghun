package com.loopers.application.payment.point;

import static org.assertj.core.api.Assertions.assertThat;

import com.loopers.domain.point.PointModel;
import com.loopers.domain.point.PointRepository;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/sql/test-fixture.sql")
class PointUseHandlerTest {

  @Autowired
  PointUseHandler pointUseHandler;

  @Autowired
  PointRepository pointRepository;


  @Test
  @DisplayName("포인트가 사용이 되어질때, 포인트가 감소한다.")
  void returnDecreasedPoint_whenPointUsed() {
    //given
    String userId = "userId";
    PointModel afterPoint = pointRepository.save(new PointModel(userId, BigInteger.valueOf(5000)));
    BigInteger usePoint = BigInteger.valueOf(500);
    //when
    pointUseHandler.use(userId, usePoint);
    //then
    PointModel currentPoint = pointRepository.get(userId).get();
    assertThat(currentPoint.getPoint()).isEqualTo(afterPoint.getPoint().subtract(usePoint));
  }
}
