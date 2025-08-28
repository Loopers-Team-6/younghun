package com.loopers.infrastructure.like;

import com.loopers.application.catalog.product.LikeEventPublisher;
import com.loopers.domain.like.LikeDecreaseEvent;
import com.loopers.domain.like.LikeIncreaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeCoreEventPublisher implements LikeEventPublisher {
  private final ApplicationEventPublisher publisher;

  public void increase(String userId, Long productId) {
    log.info("userId : {}가 productId :{}에 좋아요를 증가시켰습니다.", userId, productId);
    publisher.publishEvent(new LikeIncreaseEvent(userId, productId));
  }

  public void decrease(String userId, Long productId) {
    log.info("userId : {}가 productId :{}에 좋아요를 감소시켰습니다.", userId, productId);
    publisher.publishEvent(new LikeDecreaseEvent(userId, productId));
  }

}
