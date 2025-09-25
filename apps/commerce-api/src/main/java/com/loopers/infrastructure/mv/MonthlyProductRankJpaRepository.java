package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.RankId;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonthlyProductRankJpaRepository extends JpaRepository<MonthlyProductRank, RankId> {
  @Query("""
      SELECT m FROM MonthlyProductRank m
      WHERE m.rankId.criteriaData = :date
      order by m.ranking ASC
      """)
  List<MonthlyProductRank> findByDate(LocalDate date, Pageable pageable);

  @Query("""
        SELECT count(m)
        FROM MonthlyProductRank m
        WHERE m.rankId.criteriaData = :date
        """)
  int total(LocalDate date);
}
