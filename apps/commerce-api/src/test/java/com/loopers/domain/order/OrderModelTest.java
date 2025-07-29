package com.loopers.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.loopers.domain.order.embeded.OrderNumber;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderModelTest {

  @DisplayName("주문번호는 날짜 + UUID 앞을 따서 생성합니다.")
  @Test
  void returnOrderNumberIsDateAndUuid_whenGenerate() {
    //YYYYMMDD-UUID
    //given
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String today = formatter.format(now);
    //when
    OrderNumber orderNumber = new OrderNumber();
    String uuidPart = orderNumber.getNumber().substring(today.length() + 1); // '-' 다음 부분
    //then
    assertAll(
        () -> assertThat(orderNumber.getNumber()).startsWith(today + "-"),
        () ->  assertThat(uuidPart).matches("[0-9a-fA-F]{8}"),
        () -> assertThat(orderNumber.getNumber().length()).isEqualTo(today.length() + 1 + 8));;
  }
}
