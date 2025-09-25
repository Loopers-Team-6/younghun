package com.loopers.application.mv.writer;

import com.loopers.domain.mv.WeeklyProductRank;
import com.loopers.domain.mv.WeeklyProductRankRepository;
import java.util.ArrayList;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class WeeklyProductRankWriter implements ItemWriter<WeeklyProductRank> {
  private final WeeklyProductRankRepository repository;

  public WeeklyProductRankWriter(WeeklyProductRankRepository repository) {
    this.repository = repository;
  }

  @Override
  public void write(Chunk<? extends WeeklyProductRank> chunk) {
    repository.addAll(new ArrayList<>(chunk.getItems()));
  }
}
