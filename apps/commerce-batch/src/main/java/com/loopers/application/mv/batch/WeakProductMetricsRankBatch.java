package com.loopers.application.mv.batch;


import com.loopers.domain.metrics.ProductAggregate;
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
public class WeakProductMetricsRankBatch {
  private final PlatformTransactionManager transactionManager;
  private final JobRepository jobRepository;
  private final ItemReader<ProductAggregate> metricsReader;
  private final ItemProcessor<ProductAggregate, WeeklyProductRank> weaklyAggregateProcessor;
  private final ItemWriter<WeeklyProductRank> WeeklyProductRankWriter;
  public WeakProductMetricsRankBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                     ItemReader<ProductAggregate> metricsReader,
                                     ItemProcessor<ProductAggregate, WeeklyProductRank> weaklyAggregateProcessor,
                                     ItemWriter<WeeklyProductRank> weeklyProductRankWriter) {
    this.transactionManager = transactionManager;
    this.jobRepository = jobRepository;
    this.metricsReader = metricsReader;
    this.weaklyAggregateProcessor = weaklyAggregateProcessor;
    WeeklyProductRankWriter = weeklyProductRankWriter;
  }


  @Bean
  public Job weakJob(Step aggregateStep) {
    return new JobBuilder("weeklyAggregateJob", jobRepository)
        .start(aggregateStep)
        .build();
  }

  @Bean
  public Step aggregateStep() {
    return new StepBuilder("weeklyAggregateStep", jobRepository)
        .<ProductAggregate, WeeklyProductRank>chunk(100, transactionManager)
        .reader(metricsReader)
        .processor(weaklyAggregateProcessor)
        .writer(WeeklyProductRankWriter)
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
