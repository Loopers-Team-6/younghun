package com.loopers.domain.point.embeded;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Point {
  private int point;

  public Point() {
    this.point = 0;
  }

  // 충전
  public Point charge(int point) {
    if (point <= 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "0이하로 포인트를 충전할 수 없습니다.");
    }
    return new Point(this.point + point);
  }

  // 사용
  public Point use(int point) {
    if (point < 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "0미만으로 포인트를 사용할 수 없습니다.");
    }
    return new Point(this.point - point);
  }


  public Point(int point) {
    if (point < 0) {
      throw new CoreException(ErrorType.BAD_REQUEST, "포인트는 0미만일 수 없습니다.");
    }

    this.point = point;
  }

}
