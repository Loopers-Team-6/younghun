package com.loopers.domain.like.count;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_signal_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductSignalCountModel extends BaseEntity {
  private Long productId;
  private int likeCount;

  private ProductSignalCountModel(Long productId) {
    if (productId == null) {
      throw new CoreException(ErrorType.NOT_FOUND, "상품ID가 존재하지 않는다면 좋아요를 증감할 수 없습니다");
    }
    this.productId = productId;
  }

  private ProductSignalCountModel(Long productId, int likeCount) {
    this(productId);
    this.likeCount = likeCount;
  }

  public static ProductSignalCountModel register(Long productId) {
    return new ProductSignalCountModel(productId, 1);
  }

  public static ProductSignalCountModel of(Long productId, int count) {
    return new ProductSignalCountModel(productId, count);
  }

  public void increase(boolean isLiked) {
    if (isLiked) {
      return;
    }
    this.likeCount += 1;
  }

  public void decrease(boolean isLiked) {
    if (!isLiked) {
      return;
    }
    int likeCurrentCount = this.likeCount - 1;

    if (likeCurrentCount < 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "좋아요 count는 0미만이 될 수 없습니다.");
    }

    this.likeCount = likeCurrentCount;
  }
}
