package com.loopers.domain.like.product;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "liked_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LikedProductModel extends BaseEntity {
  private Long productId;
  private int count;

  private LikedProductModel(Long productId) {
    if (productId == null) {
      throw new CoreException(ErrorType.NOT_FOUND, "상품ID가 존재하지 않는다면 좋아요를 증감할 수 없습니다");
    }
    this.productId = productId;
  }

  private LikedProductModel(Long productId, int count) {
    this(productId);
    this.count = count;
  }

  public static LikedProductModel register(Long productId) {
    return new LikedProductModel(productId, 1);
  }

  public static LikedProductModel of(Long productId, int count) {
    return new LikedProductModel(productId, count);
  }

  public void increase(boolean isLiked) {
    if (isLiked) {
      return;
    }
    this.count += 1;
  }

  public void decrease(boolean isLiked) {
    if (!isLiked) {
      return;
    }
    int current = this.count - 1;

    if (current < 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "좋아요 count는 0미만이 될 수 없습니다.");
    }

    this.count = current;
  }
}
