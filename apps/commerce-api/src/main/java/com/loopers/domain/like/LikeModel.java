package com.loopers.domain.like;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "like")
public class LikeModel extends BaseEntity {

  private String memberId;
  private Long productId;

  public static LikeModel register(String memberId, Long productId) {
    return new LikeModel(memberId, productId);
  }

  private LikeModel(String memberId, Long productId) {
    if (memberId == null) {
      throw new CoreException(ErrorType.BAD_REQUEST, "계정 아이디는 필수입니다.");
    }
    if (productId == null) {
      throw new CoreException(ErrorType.BAD_REQUEST, "상품 아이디는 필수입니다.");
    }

    this.memberId = memberId;
    this.productId = productId;
  }

  public ZonedDateTime cancel() {
    this.delete();
    return this.getDeletedAt();
  }
}
