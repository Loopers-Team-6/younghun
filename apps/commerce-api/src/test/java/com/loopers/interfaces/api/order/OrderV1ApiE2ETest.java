package com.loopers.interfaces.api.order;

import static com.loopers.interfaces.api.ApiResponse.Metadata.Result.FAIL;
import static com.loopers.interfaces.api.ApiResponse.Metadata.Result.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.order.OrderV1Dto.Create.OrderItemRequest;
import com.loopers.interfaces.api.order.OrderV1Dto.Create.Request;
import com.loopers.utils.DatabaseCleanUp;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderV1ApiE2ETest {
  private static final String ENDPOINT = "/api/v1/orders";
  private final TestRestTemplate testRestTemplate;
  private final DatabaseCleanUp databaseCleanUp;

  @Autowired
  public OrderV1ApiE2ETest(TestRestTemplate testRestTemplate, DatabaseCleanUp databaseCleanUp) {
    this.testRestTemplate = testRestTemplate;
    this.databaseCleanUp = databaseCleanUp;
  }

  @AfterEach
  void tearDown() {
    databaseCleanUp.truncateAllTables();
  }

  @DisplayName("POST /api/v1/orders")
  @Nested
  class Create {

    @DisplayName("계정 아이디가 존재하지 않는다면, `401 Unauthorized`를 반환한다.")
    @Test
    void throw401UnauthorizedException_whenNotExitsUserId() {
      //given
      //when
      ParameterizedTypeReference<ApiResponse<OrderV1Dto.Create.Response>> responseType = new ParameterizedTypeReference<>() {
      };

      ResponseEntity<ApiResponse<OrderV1Dto.Create.Response>> response =
          testRestTemplate.exchange(ENDPOINT, HttpMethod.POST, new HttpEntity<>(null), responseType);

      //then
      assertAll(
          () -> assertThat(response.getStatusCode().is4xxClientError()).isTrue(),
          () -> assertThat(response.getBody().meta().result()).isEqualTo(FAIL)

      );
    }

    @DisplayName("주문을 생성하면 주문 정보를 리턴합니다.")
    @Test
    void returnOrderInfo_whenCreatedOrder() {
      //given
      String userId = "test";
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-USER-ID", userId);

      Long productId1 = 1L;
      int quantity1 = 2;

      Long productId2 = 1L;
      int quantity2 = 2;

      Request request = new Request(
          List.of(new OrderItemRequest(productId1, quantity1), new OrderItemRequest(productId2, quantity2))
      );

      //when
      ParameterizedTypeReference<ApiResponse<OrderV1Dto.Create.Response>> responseType = new ParameterizedTypeReference<>() {
      };

      ResponseEntity<ApiResponse<OrderV1Dto.Create.Response>> response =
          testRestTemplate.exchange(ENDPOINT, HttpMethod.POST, new HttpEntity<>(request, headers), responseType);

      //then
      assertAll(
          () -> assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(),
          () -> assertThat(response.getBody().meta().result()).isEqualTo(SUCCESS),
          () -> assertThat(response.getBody().data().userId()).isEqualTo(userId)

      );
    }

  }
}
