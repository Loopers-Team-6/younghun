package com.loopers.application.catalog.product;


import static org.assertj.core.api.Assertions.assertThat;

import com.loopers.domain.catalog.product.ProductModel;
import com.loopers.utils.DatabaseCleanUp;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

@Sql("/sql/test-fixture.sql")
@SpringBootTest
class ProductServiceIntegrationTest {

  @Autowired
  private ProductFacade productFacade;
  @Autowired
  private DatabaseCleanUp databaseCleanUp;

  @AfterEach
  void tearDown() {
    databaseCleanUp.truncateAllTables();
  }

  // 기본값이 10개
  @DisplayName("아무런 조건 없이 조회를 하는 경우, 상품 리스트 10개를 반환합니다.")
  @Test
  void returnProductList_whenSearchingNotCondition() {
    //given
    ProductCommand command = ProductCommand.builder().build();
    //when
    Page<ProductModel> search = productFacade.search(command);
    //then
    assertThat(search).isNotNull();
    assertThat(search.getContent()).hasSize(10);
    search.getContent().forEach(System.out::println);
  }


  @DisplayName("콘텐츠 갯수를 13개 조회 하는 경우, 상품 리스트 13개를 반환합니다.")
  @Test
  void returnProductListSizeIs13_whenSearchingPerContentsSizeIs13() {
    //given
    ProductCommand command = ProductCommand.builder()
        .perSize(13)
        .build();
    //when
    Page<ProductModel> search = productFacade.search(command);
    //then
    assertThat(search).isNotNull();
    assertThat(search.getContent()).hasSize(13);
    search.getContent().forEach(System.out::println);
  }

  @DisplayName("페이지 시작 갯수를 다르게 조회 하는 경우, 서로 다른 페이지의 내용은 서로 다릅니다.")
  @Test
  void returnDifferentPagesHaveDifferentContent() {
    //given
    ProductCommand command1 = ProductCommand.builder()
        .currentPage(0)
        .perSize(13)
        .build();
    ProductCommand command2 = ProductCommand.builder()
        .currentPage(1)
        .perSize(13)
        .build();
    //when
    Page<ProductModel> search = productFacade.search(command1);
    Page<ProductModel> search2 = productFacade.search(command2);
    //then
    assertThat(search).isNotEqualTo(search2);
  }

  @DisplayName("특정 브랜드 아이디로 조회하는 경우, 그 브랜드에 해당하는 상품 리스트가 조회됩니다.")
  @Test
  void returnProductListForBrandId_whenSearchingBrandId() {
    //given
    ProductCommand command = ProductCommand.builder()
        .brandId(1L)
        .currentPage(0)
        .perSize(3)
        .build();
    //when
    Page<ProductModel> search = productFacade.search(command);
    //then
    List<ProductModel> content = search.getContent();

    for (ProductModel productModel : content) {
      assertThat(productModel.getBrandId()).isEqualTo(command.brandId());
    }
  }


  @DisplayName("상품 명으로 조회하는 경우, 해당하는 상품 리스트가 조회됩니다.")
  @ParameterizedTest
  @ValueSource(strings = {"무선", "태블릿", "키보드", "아메리카노"})
  void returnProductList_whenSearchingProductName(String productName) {
    //given
    ProductCommand command = ProductCommand.builder()
        .productName(productName)
        .currentPage(0)
        .perSize(3)
        .build();
    //when
    Page<ProductModel> search = productFacade.search(command);
    //then
    List<ProductModel> content = search.getContent();

    for (ProductModel productModel : content) {
      assertThat(productModel.getName()).contains(productName);
    }
  }

  @DisplayName("생성일 기준으로 조회하는 경우, 해당하는 상품 리스트가 조회됩니다.")
  @Test
  void returnProductList_whenSortingCreatedAt() {
    //given
    ProductCommand command = ProductCommand.builder()
        .sort(SortOption.LATEST) // 기본값은 LATEST
        .currentPage(0)
        .perSize(3)
        .build();
    //when
    Page<ProductModel> search = productFacade.search(command);
    List<ProductModel> content = search.getContent();
    ProductModel preModel = content.get(0);
    //then
    for (int i = 1; i < content.size(); i++) {
      ProductModel nextModel = content.get(i);
      assertThat(preModel.getCreatedAt()).isAfterOrEqualTo(nextModel.getCreatedAt());
      preModel = nextModel;
    }
  }

  @DisplayName("가격을 기준으로 조회하는 경우, 해당하는 상품 리스트가 조회됩니다.(오름차순)")
  @Test
  void returnProductList_whenSortingPriceAsc() {
    //given
    ProductCommand command = ProductCommand.builder()
        .sort(SortOption.PRICE_ASC)
        .currentPage(0)
        .perSize(3)
        .build();
    //when
    Page<ProductModel> search = productFacade.search(command);
    List<ProductModel> content = search.getContent();
    ProductModel preModel = content.get(0);
    //then

    for (int i = 0; i < content.size(); i++) {
      System.out.println(content.get(i).getPrice().getPrice());
    }
    for (int i = 1; i < content.size(); i++) {
      ProductModel nextModel = content.get(i);
      assertThat(preModel.getPrice().getPrice().compareTo(nextModel.getPrice().getPrice()) <= 0).isTrue();
      preModel = nextModel;
    }

  }

}
