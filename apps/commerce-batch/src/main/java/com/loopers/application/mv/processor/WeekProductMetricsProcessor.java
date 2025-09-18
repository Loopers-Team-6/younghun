package com.loopers.application.mv.processor;

import com.loopers.domain.metrics.ProductAggregate;
import com.loopers.domain.mv.WeeklyProductRank;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class WeekProductMetricsProcessor implements ItemProcessor<ProductAggregate, WeeklyProductRank>{


  private final AtomicInteger rank = new AtomicInteger(1);
  private final String date;

  public WeekProductMetricsProcessor(@Value("#{jobParameters['date']}") String date) {
    this.date = date;
  }

  @Override
  public WeeklyProductRank process(ProductAggregate item) {
    return new WeeklyProductRank(item, date, rank.getAndIncrement());
  }
}
