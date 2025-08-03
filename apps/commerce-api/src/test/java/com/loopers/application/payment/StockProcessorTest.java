package com.loopers.application.payment;

import static org.assertj.core.api.Assertions.assertThat;

import com.loopers.application.payment.stock.StockDecreaseCommand;
import com.loopers.application.payment.stock.StockProcessor;
import com.loopers.domain.catalog.product.stock.StockModel;
import com.loopers.domain.catalog.product.stock.StockRepository;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/sql/test-fixture.sql")
public class StockProcessorTest {
  @Autowired
  private StockProcessor stockProcessor;
  @Autowired
  private StockRepository stockRepository;

  @DisplayName("동시에 주문해도 재고가 정상적으로 차감된다.")
  @Test
  public void concurrencyTest_stockShouldBeProperlyDecreasedWhenOrdersCreated() throws InterruptedException {
    int threadCount = 1000;
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          stockProcessor.decreaseStocks(
              List.of(new StockDecreaseCommand(7L, 10L)));
        } catch (Exception e) {
          System.out.println("실패: " + e.getMessage());
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    StockModel stockModel = stockRepository.get(7L);
    assertThat(stockModel.getStock().getStock()).isEqualTo(0);
  }
}
