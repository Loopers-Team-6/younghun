package com.loopers.application.mv.batch;


import com.loopers.domain.metrics.ProductAggregate;
import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.WeeklyProductRank;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.expression.ParseException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Configuration
public class MonthProductMetricsRankBatch {
  private final PlatformTransactionManager transactionManager;
  private final JobRepository jobRepository;

  private final ItemReader<ProductAggregate> monthlyAggregateReader;
  private final ItemProcessor<ProductAggregate, MonthlyProductRank> monthProductMetricsProcessor;
  private final ItemWriter<MonthlyProductRank> monthlyProductRankWriter;


  public MonthProductMetricsRankBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                      ItemReader<ProductAggregate> monthlyAggregateReader,
                                      ItemProcessor<ProductAggregate, MonthlyProductRank> monthProductMetricsProcessor,
                                      ItemWriter<MonthlyProductRank> monthlyProductRankWriter) {
    this.transactionManager = transactionManager;
    this.jobRepository = jobRepository;
    this.monthlyAggregateReader = monthlyAggregateReader;
    this.monthProductMetricsProcessor = monthProductMetricsProcessor;
    this.monthlyProductRankWriter = monthlyProductRankWriter;
  }


  @Bean
  public Job monthJob(Step montlyggregateStep) {
    return new JobBuilder("monthlyAggregateJob", jobRepository)
        .start(montlyggregateStep)
        .build();
  }

  @Bean
  public Step montlyggregateStep() {
    return new StepBuilder("monthlyAggregateStep", jobRepository)
        .<ProductAggregate, MonthlyProductRank>chunk(100, transactionManager)
        .reader(monthlyAggregateReader)
        .processor(monthProductMetricsProcessor)
        .writer(monthlyProductRankWriter)
        .faultTolerant()
        .skip(HttpClientErrorException.class)
        .skip(DataIntegrityViolationException.class)
        .skip(ParseException.class)
        .skipLimit(10)
        .retry(HttpServerErrorException.class)
        .retry(CannotAcquireLockException.class)
        .retry(ConnectException.class)
        .retry(SocketTimeoutException.class)
        .retryLimit(3)
        .build();
  }

}
