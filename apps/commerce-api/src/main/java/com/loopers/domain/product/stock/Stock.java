package com.loopers.domain.product.stock;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.product.stock.embeded.ProductStock;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "stock")
public class Stock extends BaseEntity {
  private Long productId;
  @Embedded
  private ProductStock stock;

  public Stock(Long productId, Long stock) {
    this.productId = productId;
    this.stock = ProductStock.of(stock);
  }


}
