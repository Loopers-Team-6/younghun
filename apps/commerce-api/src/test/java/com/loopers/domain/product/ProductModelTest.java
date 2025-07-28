package com.loopers.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.domain.product.embeded.ProductPrice;
import com.loopers.domain.user.embeded.BirthDay;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.math.BigInteger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ProductModelTest {

  @DisplayName("상품 가격을 책정할때,")
  @Nested
  class ProductPriceTest {

    @DisplayName("음수라면, `400 Bad Request`를 반환합니다.")
    @Test
    void throw400BadRequestException_whenProductPriceIsUnderZero() {
      //given
      BigInteger price = BigInteger.valueOf(-1);
      // when
      CoreException result = assertThrows(CoreException.class, () -> ProductPrice.of(price));
      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
    }
  }

}
