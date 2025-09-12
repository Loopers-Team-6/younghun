package com.loopers.infrastructure.rank;

import com.loopers.domain.rank.Rank;
import com.loopers.domain.rank.RankRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class RankRepositoryImpl implements RankRepository {
  private final RankJpaRepository repository;

  public RankRepositoryImpl(RankJpaRepository repository) {
    this.repository = repository;
  }

  public Optional<Rank> get(Long productId) {
    return repository.findByProductId(productId);
  }

  @Override
  public void increment(Long productId, Double score) {
    repository.increment(productId, score);
  }


  @Override
  public void save(Rank rank) {
    repository.save(rank);
  }
}
