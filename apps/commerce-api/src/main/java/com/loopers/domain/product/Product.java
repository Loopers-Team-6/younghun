package com.loopers.domain.product;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.product.embeded.ProductName;
import com.loopers.domain.product.embeded.ProductPrice;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product")
public class Product extends BaseEntity {
  private Long brandId;
  @Embedded
  private ProductName name;
  @Embedded
  private ProductPrice price;
  @Column(columnDefinition = "TEXT")
  private String description;

  public Product(Long brandId, String name, BigInteger price, String description) {
    this.brandId = brandId;
    this.name = ProductName.of(name);
    this.price = ProductPrice.of(price);
    this.description = description;
  }
}
