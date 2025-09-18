package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.MonthlyProductRankRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyProductRankRepositoryImpl implements MonthlyProductRankRepository {
  private final MonthlyMontlyProductRankJpaRepository repository;

  public MonthlyProductRankRepositoryImpl(MonthlyMontlyProductRankJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<MonthlyProductRank> addAll(List<MonthlyProductRank> rank) {
    return repository.saveAll(rank);
  }
}
