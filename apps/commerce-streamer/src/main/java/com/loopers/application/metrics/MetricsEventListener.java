package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsLikesEvent;
import com.loopers.domain.metrics.MetricsRepository;
import com.loopers.domain.metrics.MetricsSalesEvent;
import com.loopers.domain.metrics.MetricsViewsEvent;
import java.util.Map.Entry;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MetricsEventListener {
  private final MetricsRepository repository;

  public MetricsEventListener(MetricsRepository repository) {
    this.repository = repository;
  }

  @EventListener
  @Async
  public void sales(MetricsSalesEvent event) {
    for (Entry<Long, Long> entry : event.map().entrySet()) {
      Long productId = entry.getKey();
      Long sum = entry.getValue();
      repository.upsertSales(productId, sum);
    }
  }

  @EventListener
  @Async
  public void views(MetricsViewsEvent event) {
    for (Entry<Long, Long> entry : event.map().entrySet()) {
      Long productId = entry.getKey();
      Long sum = entry.getValue();
      repository.upsertViews(productId, sum);
    }
  }

  @EventListener
  @Async
  public void likes(MetricsLikesEvent event) {
    for (Entry<Long, Long> entry : event.map().entrySet()) {
      Long productId = entry.getKey();
      Long sum = entry.getValue();
      repository.upsertLikes(productId, sum);
    }
  }


}
