package com.loopers.interfaces.api.rank;

import com.loopers.application.rank.ProductInfo;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.time.LocalDate;
import java.util.List;

public class RankV1Dto {

  public record RankCondition(
      LocalDate date,
      Integer start,
      Integer end
  ) {
    public RankCondition {
      // 오늘 날짜로 지정한다.
      date = date == null ? LocalDate.now() : date;
      start = start == null ? 0 : start;
      end = end == null ? 10 : end;

      if (end > 20) {
        throw new CoreException(ErrorType.CONFLICT, "사이즈는 20개를 넘어설수 없습니다.");
      }
    }
  }

  public record RankResponse(
      List<Contents> contents
  ) {
    public static RankResponse from(List<ProductInfo> rank) {
      return new RankResponse(rank.stream().map(
          a -> new Contents(a.productId(), a.productName())
      ).toList());
    }
  }

  public record Contents(
      Long productId,
      String productName
  ) {
  }
}
