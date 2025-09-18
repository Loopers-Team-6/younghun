package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.RankId;
import com.loopers.domain.mv.WeeklyProductRank;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeeklyProductRankJpaRepository extends JpaRepository<WeeklyProductRank, RankId> {

  @Query("""
         SELECT p FROM WeeklyProductRank p
         WHERE p.rankId.criteriaData = :date
         order by p.ranking ASC
         """)
  List<WeeklyProductRank> findByDate(LocalDate date);
}
