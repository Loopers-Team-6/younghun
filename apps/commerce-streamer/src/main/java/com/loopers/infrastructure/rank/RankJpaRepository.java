package com.loopers.infrastructure.rank;

import com.loopers.domain.rank.Rank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RankJpaRepository extends JpaRepository<Rank, Long> {
  Optional<Rank> findByProductId(Long productId);

  @Modifying
  @Query("""
      UPDATE Rank r SET r.score = r.score + :score
      WHERE r.productId = :productId
      """)
  void increment(@Param("productId") Long productId, @Param("score") Double score);
}
