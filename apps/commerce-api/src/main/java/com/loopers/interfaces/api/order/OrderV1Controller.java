package com.loopers.interfaces.api.order;

import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.order.OrderV1Dto.Create.OrderItemResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderV1Controller {


  @PostMapping
  public ApiResponse<OrderV1Dto.Create.Response> crete(@RequestHeader("X-USER-ID") String userId,
  OrderV1Dto.Create.Request request
  ) {

    return ApiResponse.success(new OrderV1Dto.Create.Response(
        userId,
        1L,
        UUID.randomUUID().toString(),
        BigInteger.valueOf(20000),
        List.of(
            new OrderItemResponse(1L, "상품1", 2),
            new OrderItemResponse(2L, "상품2", 5)
        )
    ));
  }
}
