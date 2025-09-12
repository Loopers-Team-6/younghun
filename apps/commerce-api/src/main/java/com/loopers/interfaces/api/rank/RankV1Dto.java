package com.loopers.interfaces.api.rank;

import com.loopers.application.rank.ProductInfo;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.time.LocalDate;
import java.util.List;

public class RankV1Dto {

  public record RankCondition(
      LocalDate date,
      Integer page,
      Integer size
  ) {
    public RankCondition {
      // 오늘 날짜로 지정한다.
      date = date == null ? LocalDate.now() : date;
      page = page == null ? 0 : page;
      size = size == null ? 10 : size;

      if (size > 20) {
        throw new CoreException(ErrorType.CONFLICT, "사이즈는 20개를 넘어설수 없습니다.");
      }
    }
  }

  public record RankResponse(
      List<Contents> contents,
      int page,
      int size,
      int total
  ) {
    public static RankResponse from(ProductInfo info) {
      return new RankResponse(
          info.contents().stream().map(
              a -> new Contents(a.productId(), a.productName())
          ).toList(),
          info.page(), info.size(), info.total());
    }
  }

  public record Contents(
      Long productId,
      String productName
  ) {
  }
}
