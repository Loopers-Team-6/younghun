package com.loopers.infrastructure.catalog.product;

import com.loopers.domain.catalog.brand.QBrandModel;
import com.loopers.domain.catalog.product.ProductCriteria;
import com.loopers.domain.catalog.product.ProductModel;
import com.loopers.domain.catalog.product.ProductRepository;
import com.loopers.domain.catalog.product.QProductModel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
  private final JPAQueryFactory query;
  private final QProductModel product = QProductModel.productModel;
  private final QBrandModel brand = QBrandModel.brandModel;

  // 브랜드에서 해당 프로젝트 조회시
  public List<ProductModel> listOf(Long brandId) {
    return query.select(product)
        .from(product)
        .where(product.brandId.eq(brandId)).fetch();
  }

  // 검색
  public Page<ProductModel> search(ProductCriteria criteria, Pageable pageable) {
    Long brandId = criteria.brandId();
    String productName = criteria.productName();
    String brandName = criteria.brandName();
    SortOption sort = SortOption.valueOf(criteria.sort());

    // where 절
    BooleanBuilder where = new BooleanBuilder();
    if (brandId != null) {
      where.and(product.brandId.eq(brandId));
    }
    if (productName != null) {
      where.and(product.name.name.contains(productName));
    }
    if (brandName != null) {
      where.and(brand.name.name.contains(brandName));
    }

    // content
    List<ProductModel> content = query.select(product)
        .from(product)
        .leftJoin(brand).on(product.brandId.eq(brand.id))
        .where(where)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(
            switch (sort) {
              case LATEST -> product.createdAt.desc();
              case PRICE_ASC -> product.price.price.asc();
              case LIKES_DESC -> null;
            })
        .fetch();

    // 갯수
    Long totalCount = query.select(product.count())
        .from(product)
        .leftJoin(brand).on(product.brandId.eq(brand.id))
        .where(where)
        .fetchOne();

    Long total = Optional.ofNullable(totalCount).orElse(0L);

    return new PageImpl<>(content, pageable, total);
  }

  enum SortOption {
    LATEST, PRICE_ASC, LIKES_DESC
  }
}
