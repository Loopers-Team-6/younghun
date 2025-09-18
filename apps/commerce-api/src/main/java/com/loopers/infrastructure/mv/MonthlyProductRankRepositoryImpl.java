package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.MonthlyProductRankRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonthlyProductRankRepositoryImpl implements MonthlyProductRankRepository {
  private final MonthlyProductRankJpaRepository repository;

  @Override
  public List<MonthlyProductRank> get(LocalDate date, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return repository.findByDate(date, pageable);
  }
}
