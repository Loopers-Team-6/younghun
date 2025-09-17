package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.ProductMetrics;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;


@Configuration
public class WeeklyMetricsReader {
  private final ProductMetricsJpaRepository productMetricsRepository;

  public WeeklyMetricsReader(ProductMetricsJpaRepository productMetricsRepository) {
    this.productMetricsRepository = productMetricsRepository;
  }

  @Bean
  @StepScope
  public ItemReader<ProductMetrics> weaklyAggregateReader(@Value("#{jobParameters['date']}") String date) {
    return new RepositoryItemReaderBuilder<ProductMetrics>()
        .name("weaklyAggregateReader")
        .repository(productMetricsRepository)
        .methodName("findByDate")
        .arguments(List.of(LocalDate.parse(date)))
        .pageSize(1000)
        .sorts(Map.of("id", Sort.Direction.ASC))
        .build();
  }
}
