package com.loopers.domain.mv;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.metrics.WeeklyProductAggregate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mv_weekly_product_rank")
public class WeeklyProductRank extends BaseEntity {
  private Long productId;
  private int ranking;
  private Double score;
  private LocalDate criteriaData;
  private Long sales;
  private Long views;
  private Long likes;

  protected WeeklyProductRank() {
  }

  public WeeklyProductRank(WeeklyProductAggregate item, String date, int rank) {
    this.productId = item.getProductId();
    this.ranking = rank;
    this.score = item.getTotalScore() == null ? 0.0 : item.getTotalScore(); // null인 경우 0.0으로 하게 만든다.
    this.criteriaData = LocalDate.parse(date);
    this.sales = item.getTotalSales();
    this.views = item.getTotalViews();
    this.likes = item.getTotalLikes();
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getRank() {
    return ranking;
  }

  public void setRank(int ranking) {
    this.ranking = ranking;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public LocalDate getCriteriaData() {
    return criteriaData;
  }

  public void setCriteriaData(LocalDate criteriaData) {
    this.criteriaData = criteriaData;
  }

  public Long getSales() {
    return sales;
  }

  public void setSales(Long sales) {
    this.sales = sales;
  }

  public Long getViews() {
    return views;
  }

  public void setViews(Long views) {
    this.views = views;
  }

  public Long getLikes() {
    return likes;
  }

  public void setLikes(Long likes) {
    this.likes = likes;
  }

}
