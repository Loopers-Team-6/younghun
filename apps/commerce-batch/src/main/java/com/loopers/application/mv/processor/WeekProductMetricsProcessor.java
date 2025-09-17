package com.loopers.application.mv.processor;

import com.loopers.domain.metrics.ProductMetrics;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeekProductMetricsProcessor {


  @Bean
  public ItemProcessor<ProductMetrics, String> weaklyAggregateProcessor() {
    return ProductMetrics::toString;
  }

}
