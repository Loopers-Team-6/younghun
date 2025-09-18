package com.loopers.application.mv.processor;

import com.loopers.domain.metrics.WeeklyProductAggregate;
import com.loopers.domain.mv.WeeklyProductMetricsRank;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class WeekProductMetricsProcessor implements ItemProcessor<WeeklyProductAggregate, WeeklyProductMetricsRank>{


  private final AtomicInteger rank = new AtomicInteger(1);
  private final String date;

  public WeekProductMetricsProcessor(@Value("#{jobParameters['date']}") String date) {
    this.date = date;
  }

  @Override
  public WeeklyProductMetricsRank process(WeeklyProductAggregate item) {
    return new WeeklyProductMetricsRank(item, date, rank.getAndIncrement());
  }
}
