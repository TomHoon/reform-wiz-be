package com.reform.wiz.dto;

import java.time.LocalDate;

import com.reform.wiz.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

  private Long mno;

  private String id;

  private String password;

  private String name;

  private String nickname;

  private String phone;

  private String email;

  private Boolean isCompany;

  private LocalDate createdAt;

  private Boolean isDel;

  private String bizNum;

  private String accountNumber;

  public MemberDTO(MemberEntity member) {
    this.mno = member.getMno();
    this.id = member.getId();
    this.password = member.getPassword();
    this.name = member.getName();
    this.nickname = member.getNickname();
    this.phone = member.getPhone();
    this.email = member.getEmail();
    this.isCompany = member.getIsCompany();
    this.createdAt = member.getCreatedAt();
    this.isDel = member.getIsDel();
    this.bizNum = member.getBizNum();
    this.accountNumber = member.getAccountNumber();
  }

  public MemberEntity toEntity() {
    return MemberEntity.builder()
        .id(this.id)
        .password(this.password)
        .name(this.name)
        .nickname(this.nickname)
        .phone(this.phone)
        .email(this.email)
        .isCompany(this.isCompany)
        .bizNum(this.bizNum)
        .accountNumber(this.accountNumber)
        .isDel(false)
        .build();
  }

}
