package com.loopers.domain.point;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.point.embeded.Point;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class PointModel extends BaseEntity {

  private String memberId;
  @Embedded
  private Point point;

  public PointModel(String memberId) {
    validate(memberId);
    this.memberId = memberId;
    this.point = new Point();
  }

  public PointModel(String memberId, int point) {
    validate(memberId);
    this.memberId = memberId;
    this.point = new Point(point);
  }

  private void validate(String memberId) {
    if (memberId == null) {
      throw new CoreException(ErrorType.NOT_FOUND, "포인트 저장시 계정아이디는 필수입니다.");
    }
  }

  public void charge(int point) {
    this.point = this.point.charge(point);
  }


  public int getPoint() {
    return point.getPoint();
  }


}
