package com.loopers.domain.mv;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class RankId implements Serializable {

  private Long productId;
  private LocalDate criteriaData;

  protected RankId() {
  }

  public RankId(Long productId, LocalDate date) {
    this.productId = productId;
    this.criteriaData = date;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public LocalDate getCriteriaData() {
    return criteriaData;
  }

  public void setCriteriaData(LocalDate criteriaData) {
    this.criteriaData = criteriaData;
  }

}
