package com.loopers.domain.metrics;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "product_metrics")
public class ProductMetrics extends BaseEntity {
  private Long productId;
  private LocalDate date;
  private Long views;
  private Long sales;
  private Long likes;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Long getViews() {
    return views;
  }

  public void setViews(Long views) {
    this.views = views;
  }

  public Long getSales() {
    return sales;
  }

  public void setSales(Long sales) {
    this.sales = sales;
  }

  public Long getLikes() {
    return likes;
  }

  public void setLikes(Long likes) {
    this.likes = likes;
  }

  @Override
  public String toString() {
    return "ProductMetrics{" +
        "productId=" + productId +
        ", date=" + date +
        ", views=" + views +
        ", sales=" + sales +
        ", likes=" + likes +
        '}';
  }
}
