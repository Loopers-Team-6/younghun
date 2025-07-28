package com.loopers.domain.brand;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.brand.embeded.BrandName;
import jakarta.persistence.Column;
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

  private String memberId;

  @Embedded
  private BrandName name;

}

