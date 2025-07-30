package com.loopers.domain.user;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.user.embeded.BirthDay;
import com.loopers.domain.user.embeded.Email;
import com.loopers.domain.user.embeded.MemberId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;

@Entity
@Table(name = "member")
public class UserModel extends BaseEntity {

  @Embedded
  private MemberId memberId;

  @Embedded
  private Email email;

  @Embedded
  private BirthDay birthday;

  private String gender;

  public UserModel() {
  }

  @Builder(builderMethodName = "create")
  public UserModel(String memberId, String email, String birthday, String gender) {
    this.memberId = new MemberId(memberId);
    this.email = new Email(email);
    this.birthday = new BirthDay(birthday);
    this.gender = gender;
  }


  public String getMemberId() {
    return memberId.getMemberId();
  }

  public String getEmail() {
    return email.getEmail();
  }

  public LocalDate getBirthday() {
    return birthday.getBirthday();
  }


  public String getGender() {
    return gender;
  }

}
