package com.loopers.domain.weight;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "weight")
public class Weight extends BaseEntity {
  private Double views;
  private Double sales;
  private Double likes;

  public Weight() {
  }

  public Weight(Double views, Double sales, Double likes) {
    this.views = views;
    this.sales = sales;
    this.likes = likes;
  }

  public Double getViews() {
    return views;
  }

  public void setViews(Double views) {
    this.views = views;
  }

  public Double getSales() {
    return sales;
  }

  public void setSales(Double sales) {
    this.sales = sales;
  }

  public Double getLikes() {
    return likes;
  }

  public void setLikes(Double likes) {
    this.likes = likes;
  }
}
