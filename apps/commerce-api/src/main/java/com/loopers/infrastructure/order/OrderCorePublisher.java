package com.loopers.infrastructure.order;

import com.loopers.data_platform.PlatformSendEvent;
import com.loopers.domain.order.OrderCouponRegisterCommand;
import com.loopers.domain.order.OrderPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCorePublisher implements OrderPublisher {
  private final ApplicationEventPublisher publisher;

  public void publish(String userId, Long orderId, Long couponId) {
    log.info("userId: {}가 orderId: {}에 couponId {}를 사용하였습니다.", userId, orderId, couponId);
    publisher.publishEvent(new OrderCouponRegisterCommand(userId, orderId, couponId));
  }

  public void publish(Long orderId, String payload) {
    log.info("주문 정보에서 데이터 플랫폼으로 데이터를 전송합니다.");
    publisher.publishEvent(new PlatformSendEvent("주문", orderId, payload));
  }

}
