package com.loopers.infrastructure.weight;

import com.loopers.domain.weight.Weight;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeightJpaRepository extends JpaRepository<Weight, Long> {


  @Query("""
        SELECT w FROM Weight w
        ORDER BY w.updatedAt DESC
        """)
  List<Weight> findBy(Pageable pageable);
}
