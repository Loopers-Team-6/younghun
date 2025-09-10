package com.loopers.application.catalog.product;

import com.loopers.application.like.LikePublisher;
import com.loopers.domain.catalog.product.status.ProductStatusRepository;
import com.loopers.domain.like.LikeDecreaseEvent;
import com.loopers.domain.like.LikeIncreaseEvent;
import com.loopers.domain.like.LikeMetricsMessage;
import com.loopers.support.shared.Message;
import com.loopers.support.shared.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductLikeListener {
  private final ProductStatusRepository repository;
  private final MessageConverter converter;
  private final LikePublisher publisher;

  @Async
  @EventListener
  public void increase(LikeIncreaseEvent event) {
    repository.increase(event.productId());
    publisher.aggregate(generate(event.productId(), 1), event.productId());
  }


  @Async
  @EventListener
  public void decrease(LikeDecreaseEvent event) {
    repository.decrease(event.productId());
    publisher.aggregate(generate(event.productId(), -1), event.productId());
    if (event.current() == 0) {
      publisher.evict(event.productId());
    }
  }

  private Message generate(Long productId, Integer data) {
    return new Message(converter.convert(new LikeMetricsMessage(productId, data)));
  }

}
