package com.loopers.application.mv.batch;


import com.loopers.domain.metrics.WeeklyProductAggregate;
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
  private final ItemReader<WeeklyProductAggregate> metricsReader;
  private final ItemProcessor<WeeklyProductAggregate, String> weaklyAggregateProcessor;

  public WeakProductMetricsRankBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                     ItemReader<WeeklyProductAggregate> metricsReader,
                                     ItemProcessor<WeeklyProductAggregate, String> weaklyAggregateProcessor) {
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
        .<WeeklyProductAggregate, String>chunk(1000, transactionManager)
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
