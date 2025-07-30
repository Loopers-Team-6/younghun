package com.loopers.domain.like;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LikeModelTest {

  @Test
  @DisplayName("계정 아이디를 입력하지 않는다면, `400 BadRequest`를 반환한다.")
  void throw400BadRequestException_whenInputUserId() {
    //given
    String userId = null;
    Long productId = 1L;
    //when
    CoreException result = assertThrows(CoreException.class, () -> LikeModel.register(userId, productId));
    //then
    assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
  }


  @Test
  @DisplayName("상품 아이디를 입력하지 않는다면, `400 BadRequest`를 반환한다.")
  void throw400BadRequestException_whenInputProductId() {
    //given
    String userId = "user";
    Long productId = null;
    //when
    CoreException result = assertThrows(CoreException.class, () ->  LikeModel.register(userId, productId));
    //then
    assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
  }


  @Test
  @DisplayName("최초로 상품에 좋아요를 하는 경우,`좋아요`상태가 true가 된다..")
  void returnTrue_whenLikedProduct() {
    //given
    String userId = "user";
    Long productId = 1L;
    //when
    LikeModel liked = LikeModel.register(userId, productId);
    //then
    assertThat(liked.isLiked()).isTrue();
  }


  @Test
  @DisplayName("상품에 좋아요 해제를 하는 경우,`좋아요`상태가 false가 된다..")
  void returnFalse_whenUnLikedProduct() {
    //given
    String userId = "user";
    Long productId = 1L;
    LikeModel liked = LikeModel.register(userId, productId);
    //when
    liked.unLike();
    //then
    assertThat(liked.isLiked()).isFalse();
  }


  @Test
  @DisplayName("상품에 좋아요 해제된 상품에 좋아요를 하는 경우,`좋아요`상태가 true가 된다..")
  void returnLiTrue_whenLikedProductAgain() {
    //given
    String userId = "user";
    Long productId = 1L;
    LikeModel liked = LikeModel.register(userId, productId);
    liked.unLike();
    //when
    liked.like();
    //then
    assertThat(liked.isLiked()).isTrue();
  }




}
