package com.loopers.domain.brand;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.brand.embeded.BrandName;
import com.loopers.domain.brand.embeded.Products;
import com.loopers.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

