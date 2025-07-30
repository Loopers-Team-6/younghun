package com.loopers.domain.like;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LikeModelTest {

  @Test
  @DisplayName("계정 아이디를 입력하지 않는다면, `400 BadRequest`를 반환한다.")
  void throw400BadRequestException_whenInputMemberId() {
    //given
    String memberId = null;
    Long productId = 1L;
    //when
    CoreException result = assertThrows(CoreException.class, () -> LikeModel.register(memberId, productId));
    //then
    assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
  }


  @Test
  @DisplayName("상품 아이디를 입력하지 않는다면, `400 BadRequest`를 반환한다.")
  void throw400BadRequestException_whenInputProductId() {
    //given
    String memberId = "user";
    Long productId = null;
    //when
    CoreException result = assertThrows(CoreException.class, () ->  LikeModel.register(memberId, productId));
    //then
    assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
  }

  @Test
  @DisplayName("특정 상품을 좋아요를 누른다면, `좋아요`가 등록된다.")
  void returnLikeInfo_whenInputMemberIdAndProductId() {
    //given
    String memberId = "user";
    Long productId = 1L;
    //when
    LikeModel likeModel = LikeModel.register(memberId, productId);
    //then
    assertAll(() -> assertThat(likeModel.getMemberId().equals(memberId)),
              () -> assertThat(likeModel.getProductId()).isEqualTo(productId));
  }


  @Test
  @DisplayName("좋아요가 된 상품을 해제 한다면, `좋아요`가 삭제된다.")
  void returnRemoveLikeInfo_whenCancelLike() {
    //given
    String memberId = "user";
    Long productId = 1L;
    LikeModel likeModel =  LikeModel.register(memberId, productId);
    //when
    ZonedDateTime cancelTime = likeModel.cancel();
    //then
    assertThat(likeModel.getDeletedAt()).isEqualTo(cancelTime);
  }


}
