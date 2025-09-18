package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.WeeklyProductRank;
import com.loopers.domain.mv.WeeklyProductRankRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WeeklyProductRankRepositoryImpl implements WeeklyProductRankRepository {
  private final WeeklyProductRankJpaRepository repository;

  public WeeklyProductRankRepositoryImpl(WeeklyProductRankJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public  List<WeeklyProductRank> addAll(List<WeeklyProductRank> rank) {
    return repository.saveAll(rank);
  }
}
