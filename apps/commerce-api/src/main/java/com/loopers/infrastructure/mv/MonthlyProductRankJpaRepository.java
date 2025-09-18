package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.MonthlyProductRank;
import com.loopers.domain.mv.RankId;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonthlyProductRankJpaRepository extends JpaRepository<MonthlyProductRank, RankId> {
  @Query("""
         SELECT p FROM WeeklyProductRank p
         WHERE p.rankId.criteriaData = :date
         order by p.ranking ASC
         """)
  List<MonthlyProductRank> findByDate(LocalDate date);
}
