package com.loopers.domain.like;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_like")
public class LikeModel extends BaseEntity {

  private String userId;
  private Long productId;
  private boolean liked;

  public static LikeModel register(String userId, Long productId) {
    return new LikeModel(userId, productId, true);
  }

  private LikeModel(String userId, Long productId, boolean liked) {
    if (userId == null) {
      throw new CoreException(ErrorType.BAD_REQUEST, "계정 아이디는 필수입니다.");
    }
    if (productId == null) {
      throw new CoreException(ErrorType.BAD_REQUEST, "상품 아이디는 필수입니다.");
    }

    this.userId = userId;
    this.productId = productId;
    this.liked = liked;
  }

  public void like() {
    // 좋아요가 눌렸을때
    if (liked) {
      return;
    }

    // 반영
    this.liked = true;
  }

  public void unLike() {
    if (!liked) {
      return;
    }

    this.liked = false;
  }


}
