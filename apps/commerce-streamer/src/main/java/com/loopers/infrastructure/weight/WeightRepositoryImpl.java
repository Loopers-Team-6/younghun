package com.loopers.infrastructure.weight;

import com.loopers.domain.weight.Weight;
import com.loopers.domain.weight.WeightRepository;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class WeightRepositoryImpl implements WeightRepository {
  private final WeightJpaRepository repository;

  public WeightRepositoryImpl(WeightJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Weight> get() {
    Weight weight = repository.findBy(PageRequest.of(0, 1)).getFirst();
    return Optional.of(weight);
  }
}
