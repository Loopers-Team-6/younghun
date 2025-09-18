package com.loopers.domain.mv;

import com.loopers.domain.metrics.WeeklyProductAggregate;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mv_weekly_product_rank")
public class WeeklyProductRank{

  @EmbeddedId
  private RankId rankId;
  private int ranking;
  private Double score;
  private Long sales;
  private Long views;
  private Long likes;

  protected WeeklyProductRank() {
  }

  public WeeklyProductRank(WeeklyProductAggregate item, String date, int rank) {


    this.ranking = rank;
    this.rankId = new RankId(item.getProductId(), LocalDate.parse(date));
    this.score = item.getTotalScore() == null ? 0.0 : item.getTotalScore(); // null인 경우 0.0으로 하게 만든다.
    this.sales = item.getTotalSales();
    this.views = item.getTotalViews();
    this.likes = item.getTotalLikes();
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
