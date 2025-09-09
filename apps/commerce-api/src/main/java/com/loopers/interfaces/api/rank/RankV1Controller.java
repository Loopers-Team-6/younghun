package com.loopers.interfaces.api.rank;

import com.loopers.application.rank.RankFacade;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.rank.RankV1Dto.RankCondition;
import com.loopers.interfaces.api.rank.RankV1Dto.RankResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rankings")
public class RankV1Controller {
  private final RankFacade rankFacade;


  @GetMapping
  public ApiResponse<RankResponse> rank(
      @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "page", required = false) Integer page
  ) {
    RankCondition condition = new RankCondition(date, page, size);
    return ApiResponse.success(RankResponse.from(rankFacade.rank(condition)));
  }
}
