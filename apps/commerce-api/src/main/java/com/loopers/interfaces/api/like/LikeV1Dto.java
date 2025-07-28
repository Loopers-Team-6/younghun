package com.loopers.interfaces.api.like;

public class LikeV1Dto {

  class Register {
    record Response(String userId, Long productId, boolean status) {}
  }
  class Unregister {
    record Response(String userId, Long productId, boolean status) {}
  }

}
