package com.loopers.domain.product.embeded;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Getter;

@Embeddable
@Getter
public class ProductPrice {
  private BigDecimal price;

  public ProductPrice() {
  }

  private ProductPrice(BigDecimal price) {
    this.price = price;
  }

  public ProductPrice of(BigDecimal price) {
    return new ProductPrice(price);
  }
}
