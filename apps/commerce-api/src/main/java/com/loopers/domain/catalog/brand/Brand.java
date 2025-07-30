package com.loopers.domain.catalog.brand;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.catalog.brand.embeded.BrandName;
import com.loopers.domain.catalog.brand.embeded.Products;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "brand")
public class Brand extends BaseEntity {

  private String userId;

  @Embedded
  private Products products;

  @Embedded
  private BrandName name;

}

