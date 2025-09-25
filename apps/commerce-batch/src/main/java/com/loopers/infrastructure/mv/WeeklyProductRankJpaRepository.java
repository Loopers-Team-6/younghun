package com.loopers.infrastructure.mv;

import com.loopers.domain.mv.WeeklyProductRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyProductRankJpaRepository extends JpaRepository<WeeklyProductRank, Long> {
}
