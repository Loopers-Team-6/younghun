package com.loopers.domain.metrics;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "product_metrics")
public class MetricsModel extends BaseEntity {
  private Long productId;
  private Long views;
  private Long likes;
  private Long sales;
  private Double score;
  private LocalDate date;

  protected MetricsModel() {
  }

  public MetricsModel(Long productId, Long views, Long likes, Long sales, Double score, LocalDate date) {
    this.productId = productId;
    this.views = views;
    this.likes = likes;
    this.sales = sales;
    this.score = score;
    this.date = date;
  }

  public void updateViews(double weight) {
    this.views++;
    this.score += (1 * weight);
  }

  public void updateLikes(long like, double weight) {
    this.likes += like;
    this.score += (like * weight);
  }

  public void updateSales(Long sales, double weight) {
    this.sales += sales;
    this.score += (sales * weight);
  }


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
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

  public Long getSales() {
    return sales;
  }

  public void setSales(Long sales) {
    this.sales = sales;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Double getScore() {
    return score;
  }
}
