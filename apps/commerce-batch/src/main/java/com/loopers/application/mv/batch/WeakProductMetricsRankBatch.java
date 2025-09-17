package com.loopers.application.mv.batch;


import com.loopers.domain.metrics.ProductMetrics;
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
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class WeakProductMetricsRankBatch {
  private final PlatformTransactionManager transactionManager;
  private final JobRepository jobRepository;
  private final ItemReader<ProductMetrics> metricsReader;
  private final ItemProcessor<ProductMetrics, String> weaklyAggregateProcessor;

  public WeakProductMetricsRankBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                     ItemReader<ProductMetrics> metricsReader,
                                     ItemProcessor<ProductMetrics, String> weaklyAggregateProcessor) {
    this.transactionManager = transactionManager;
    this.jobRepository = jobRepository;
    this.metricsReader = metricsReader;
    this.weaklyAggregateProcessor = weaklyAggregateProcessor;
  }


  @Bean
  public Job weakJob(Step aggregateStep) {
    return new JobBuilder("weakJob", jobRepository)
        .start(aggregateStep)
        .build();
  }

  @Bean
  public Step aggregateStep(ItemWriter<String> writer) {
    return new StepBuilder("step1", jobRepository)
        .<ProductMetrics, String>chunk(1000, transactionManager)
        .reader(metricsReader)
        .processor(weaklyAggregateProcessor)
        .writer(writer)
        .build();
  }



  @Bean
  public ItemWriter<String> writer() {
    return items -> items.forEach(System.out::println);
  }

}
