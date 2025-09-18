package com.loopers.infrastructure.metrics.reader;

import com.loopers.domain.metrics.ProductMetrics;
import com.loopers.infrastructure.metrics.ProductMetricsJpaRepository;
import java.time.LocalDate;
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

    LocalDate endDate = LocalDate.parse(date);                       // 선택한 날짜
    LocalDate startDate = endDate.minusDays(7);        // 7일 전

    return new RepositoryItemReaderBuilder<ProductMetrics>()
        .name("weaklyAggregateReader")
        .repository(productMetricsRepository)
        .methodName("findByDateRange")
        .arguments(startDate,endDate)
        .pageSize(1000)
        .sorts(Map.of("id", Sort.Direction.ASC))
        .build();
  }
}
