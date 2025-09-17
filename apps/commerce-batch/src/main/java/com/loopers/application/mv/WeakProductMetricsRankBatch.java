package com.loopers.application.mv;


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

  public WeakProductMetricsRankBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                     ItemReader<ProductMetrics> metricsReader) {
    this.transactionManager = transactionManager;
    this.jobRepository = jobRepository;
    this.metricsReader = metricsReader;
  }


  @Bean
  public Job weakJob(Step aggregateStep) {
    return new JobBuilder("weakJob", jobRepository)
        .start(aggregateStep)
        .build();
  }

  @Bean
  public Step aggregateStep(ItemProcessor<ProductMetrics, String> processor,
                            ItemWriter<String> writer) {
    return new StepBuilder("step1", jobRepository)
        .<ProductMetrics, String>chunk(10, transactionManager)
        .reader(metricsReader)
        .processor(processor)
        .writer(writer)
        .build();
  }


  @Bean
  public ItemProcessor<ProductMetrics, String> processor() {
    return item -> item.toString(); // 단순 변환
  }

  @Bean
  public ItemWriter<String> writer() {
    return items -> items.forEach(System.out::println);
  }

}
