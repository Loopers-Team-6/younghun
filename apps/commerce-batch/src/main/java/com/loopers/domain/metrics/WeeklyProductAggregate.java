package com.loopers.domain.metrics;

public class WeeklyProductAggregate {
  private Long productId;
  private Double totalScore;
  private Long totalViews;
  private Long totalLikes;
  private Long totalSales;

  public WeeklyProductAggregate(Long productId, Double totalScore, Long totalViews,
                             Long totalLikes, Long totalSales) {
    this.productId = productId;
    this.totalScore = totalScore;
    this.totalViews = totalViews;
    this.totalLikes = totalLikes;
    this.totalSales = totalSales;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(Double totalScore) {
    this.totalScore = totalScore;
  }

  public Long getTotalViews() {
    return totalViews;
  }

  public void setTotalViews(Long totalViews) {
    this.totalViews = totalViews;
  }

  public Long getTotalLikes() {
    return totalLikes;
  }

  public void setTotalLikes(Long totalLikes) {
    this.totalLikes = totalLikes;
  }

  public Long getTotalSales() {
    return totalSales;
  }

  public void setTotalSales(Long totalSales) {
    this.totalSales = totalSales;
  }

  @Override
  public String toString() {
    return "WeeklyProductAggregate{" +
        "productId=" + productId +
        ", totalScore=" + totalScore +
        ", totalViews=" + totalViews +
        ", totalLikes=" + totalLikes +
        ", totalSales=" + totalSales +
        '}';
  }
}
