package com.loopers.domain.product.embeded;

import jakarta.persistence.Embeddable;
import java.math.BigInteger;
import lombok.Getter;

@Embeddable
@Getter
public class ProductPrice {
  private BigInteger price;

  public ProductPrice() {
  }

  private ProductPrice(BigInteger price) {
    this.price = price;
  }

  public static ProductPrice of(BigInteger price) {
    return new ProductPrice(price);
  }
}
