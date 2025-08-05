package com.loopers.application.payment.point;

import static org.assertj.core.api.Assertions.assertThat;

import com.loopers.application.payment.handler.PointUseHandler;
import com.loopers.domain.point.PointModel;
import com.loopers.domain.point.PointRepository;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.utils.DatabaseCleanUp;
import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
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

  @Autowired
  PointJpaRepository pointJpaRepository;
  
  @Autowired
  private DatabaseCleanUp databaseCleanUp;
  @AfterEach
  void tearDown() {
    databaseCleanUp.truncateAllTables();
  }
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

  @DisplayName("동일한 유저가 여러 기기에서 동시에 주문에도, 포인트가 중복 차감되지 않아야 한다.")
  @Test
  void concurrencyTest_pointShouldBeProperlyDontDuplicatedWhenOrdersCreated() throws InterruptedException {
    // Given
    pointRepository.save(new PointModel("userId", BigInteger.valueOf(10_000)));

    int threadCount = 100;
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    AtomicInteger successCount = new AtomicInteger();
    AtomicInteger failCount = new AtomicInteger();

    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          pointUseHandler.use("userId", BigInteger.valueOf(1));
          successCount.incrementAndGet();
        } catch (Exception e) {
          failCount.incrementAndGet();
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    // When
    PointModel pointModel = pointJpaRepository.findByUserId("userId").orElseThrow();

    // Then
    BigInteger expected = BigInteger.valueOf(10_000 - successCount.get());

    System.out.println("성공: " + successCount.get());
    System.out.println("실패: " + failCount.get());
    System.out.println("최종 포인트: " + pointModel.getPoint());
    System.out.println("버전 값: " + pointModel.getVersion());

    assertThat(pointModel.getPoint()).isEqualTo(expected);
    assertThat(successCount.get() + failCount.get()).isEqualTo(threadCount);
  }
}
