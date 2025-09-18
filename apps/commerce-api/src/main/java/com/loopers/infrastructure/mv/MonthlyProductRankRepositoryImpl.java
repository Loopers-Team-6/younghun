package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.MonthlyProductRankRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonthlyProductRankRepositoryImpl implements MonthlyProductRankRepository {
  private final MonthlyProductRankJpaRepository repository;
  @Override
  public List<MonthlyProductRank> get(LocalDate date) {
    return repository.findByDate(date);
  }
}
