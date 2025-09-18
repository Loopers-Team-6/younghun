package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.WeeklyProductRank;
import com.loopers.domain.mv.WeeklyProductRankRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WeeklyProductRankRepositoryImpl implements WeeklyProductRankRepository {
  private final WeeklyProductRankJpaRepository repository;

  @Override
  public List<WeeklyProductRank> get(LocalDate date, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return repository.findByDate(date, pageable);
  }

  @Override
  public int total(LocalDate date) {
    return repository.total(date);
  }
}
