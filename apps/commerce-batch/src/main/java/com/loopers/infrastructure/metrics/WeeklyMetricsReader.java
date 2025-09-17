package com.loopers.infrastructure.metrics;

import com.loopers.domain.metrics.ProductMetrics;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
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
    RepositoryItemReader<ProductMetrics> reader = new RepositoryItemReader<>();
    reader.setRepository(productMetricsRepository);
    reader.setMethodName("findByDate");
    reader.setArguments(List.of(LocalDate.parse(date))); // JobParameters 활용
    reader.setPageSize(1000); // 페이징 단위
    reader.setSort(Map.of("id", Sort.Direction.ASC));
    return reader;
  }
}
