package com.loopers.interfaces.api.product;

import com.loopers.application.catalog.product.ProductFacade;
import com.loopers.interfaces.api.ApiResponse;
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
  public ApiResponse<ProductV1Dto.Get.Response> get(@PathVariable Long productId) {
    return ApiResponse.success(ProductV1Dto.Get.Response.from(productFacade.get(productId)));
  }
}
