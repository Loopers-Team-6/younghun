package com.loopers.application.mv.processor;

import com.loopers.domain.metrics.ProductAggregate;
import com.loopers.domain.mv.MonthlyProductRank;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class MonthProductMetricsProcessor implements ItemProcessor<ProductAggregate, MonthlyProductRank>{


  private final AtomicInteger rank = new AtomicInteger(1);
  private final String date;

  public MonthProductMetricsProcessor(@Value("#{jobParameters['date']}") String date) {
    this.date = date;
  }

  @Override
  public MonthlyProductRank process(ProductAggregate item) {
    return new MonthlyProductRank(item, date, rank.getAndIncrement());
  }
}
