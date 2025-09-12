package com.loopers.domain.rank;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_ranking")
public class Rank extends BaseEntity {
  private Long productId;
  private Double score;

  protected Rank() {
  }

  public Rank(Long productId, double score) {
    this.productId = productId;
    this.score = score;
  }

  public void increase(Double newScore) {
    this.score += newScore;
  }

  public Long getProductId() {
    return productId;
  }

  public Double getScore() {
    return score;
  }
}
