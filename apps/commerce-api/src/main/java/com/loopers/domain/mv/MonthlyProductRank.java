package com.loopers.domain.mv;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mv_monthly_product_rank")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MonthlyProductRank {
  @EmbeddedId
  private RankId rankId;
  private int ranking;
  private Double score;
  private Long sales;
  private Long views;
  private Long likes;


}
