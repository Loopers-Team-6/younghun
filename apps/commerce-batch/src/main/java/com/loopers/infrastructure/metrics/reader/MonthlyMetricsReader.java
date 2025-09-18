package com.loopers.infrastructure.metrics.reader;

import com.loopers.domain.metrics.ProductAggregate;
import com.loopers.infrastructure.metrics.ProductMetricsJpaRepository;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;


@Configuration
public class MonthlyMetricsReader {
  private final ProductMetricsJpaRepository productMetricsRepository;

  public MonthlyMetricsReader(ProductMetricsJpaRepository productMetricsRepository) {
    this.productMetricsRepository = productMetricsRepository;
  }

  @Bean
  @StepScope
  public ItemReader<ProductAggregate> monthlyAggregateReader(@Value("#{jobParameters['date']}") String date) {

    LocalDate endDate = LocalDate.parse(date);                       // 선택한 날짜
    LocalDate startDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), 1);

    return new RepositoryItemReaderBuilder<ProductAggregate>()
        .name("monthlyAggregateReader")
        .repository(productMetricsRepository)
        .methodName("findByDateRange")
        .arguments(startDate, endDate)
        .pageSize(100)
        .maxItemCount(100)
        .sorts(Map.of("productId", Direction.DESC))
        .build();
  }
}
