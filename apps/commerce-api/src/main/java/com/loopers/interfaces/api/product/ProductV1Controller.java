package com.loopers.interfaces.api.product;

import com.loopers.application.catalog.product.ProductFacade;
import com.loopers.application.catalog.product.ProductGetInfo;
import com.loopers.interfaces.api.ApiResponse;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductV1Controller {
  private final ProductFacade productFacade;

  @GetMapping
  public ApiResponse<ProductV1Dto.Search.Response> search(
      @ModelAttribute ProductV1Condition condition
  ) {
    return ApiResponse.success(
        ProductV1Dto.Search.Response.from(productFacade.search(condition.toCommand()))
    );
  }

  @GetMapping("/{productId}")
  public ApiResponse<ProductV1Dto.Get.Response> search(@PathVariable Long productId) {
    return ApiResponse.success(
        new ProductV1Dto.Get.Response(
            productId,"상품1", BigInteger.valueOf(2000),"이건 검정색이고 암튼 좋아요",
            LocalDateTime.now(),LocalDateTime.now()
        )
    );
  }
}
