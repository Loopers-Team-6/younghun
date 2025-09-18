package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.MonthlyProductRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyMontlyProductRankJpaRepository extends JpaRepository<MonthlyProductRank, Long> {
}
