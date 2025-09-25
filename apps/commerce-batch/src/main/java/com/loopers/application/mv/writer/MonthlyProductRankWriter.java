package com.loopers.application.mv.writer;

import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.MonthlyProductRankRepository;
import java.util.ArrayList;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class MonthlyProductRankWriter implements ItemWriter<MonthlyProductRank> {
  private final MonthlyProductRankRepository repository;

  public MonthlyProductRankWriter(MonthlyProductRankRepository repository) {
    this.repository = repository;
  }

  @Override
  public void write(Chunk<? extends MonthlyProductRank> chunk) throws Exception {
    repository.addAll(new ArrayList<>(chunk.getItems()));
  }
}
