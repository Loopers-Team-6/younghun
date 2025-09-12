package com.loopers.application.metrics;

import com.loopers.domain.metrics.MetricsLikesEvent;
import com.loopers.domain.metrics.MetricsSalesEvent;
import com.loopers.domain.metrics.MetricsViewsEvent;
import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MetricsPublisher {
  private final ApplicationEventPublisher publisher;

  public MetricsPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public void sales(Map<Long, Long> map) {
    publisher.publishEvent(new MetricsSalesEvent(map));
  }

  public void views(Map<Long, Long> map) {
    publisher.publishEvent(new MetricsViewsEvent(map));
  }

  public void likes(Map<Long, Long> map) {
    publisher.publishEvent(new MetricsLikesEvent(map));
  }

}
