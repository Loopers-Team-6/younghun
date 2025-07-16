package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserInfo;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.validation.constraints.NotNull;

public class UserV1Dto {

  public record UserRequest(String userId, String email, String birthday, @NotNull Gender gender) {

    public UserInfo to() {
      return new UserInfo(
          userId,
          email,
          birthday,
          gender.toString()
      );
    }
  }

  public record UserResponse(String userId, String email, String birthday, Gender gender) {
    public static UserResponse from(UserInfo info) {
      return new UserResponse(
          info.userId(),
          info.email(),
          info.birthday(),
          Gender.valueOf(info.gender())
      );
    }
  }

  public record UserGetResponse(String userId, String email, String birthday, Gender gender) {
    public static UserGetResponse from(UserInfo user) {
      return new UserGetResponse(
          user.userId(),
          user.email(),
          user.birthday(),
          Gender.valueOf(user.gender())
      );
    }
  }

  enum Gender {
    M, F
  }
}
