package com.loopers.application.order.checker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.application.order.PointValidator;
import com.loopers.domain.point.PointModel;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import com.loopers.utils.DatabaseCleanUp;
import java.math.BigInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PointValidatorTest {

  @Autowired
  private PointJpaRepository pointJpaRepository;

  @Autowired
  private PointValidator pointValidator;

  @Autowired
  private DatabaseCleanUp databaseCleanUp;

  @AfterEach
  void tearDown() {
    databaseCleanUp.truncateAllTables();
  }

  @Test
  @DisplayName("소지하고 있는 포인트보다 사용할 포인트가 더 큰 경우 `400 Bad Request`를 반환합니다.")
  void return400BadRequest_whenHasPointMoreThanUsePoint() {
    //given
    String userId = "userId";
    pointJpaRepository.save(new PointModel(userId, BigInteger.ONE));
    //when
    CoreException result = assertThrows(CoreException.class, () -> pointValidator.usePointCheck(userId, BigInteger.TEN));
    //then
    assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
  }
}
