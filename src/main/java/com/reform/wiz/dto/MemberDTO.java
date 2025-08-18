package com.reform.wiz.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.reform.wiz.entity.MemberEntity;
import com.reform.wiz.utils.MemberRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

  private Long mno;

  private String memberId;

  private String password;

  private String name;

  private String nickname;

  private String phone;

  private String role;

  private String email;

  private Boolean isCompany;

  private LocalDate createdAt;

  private Boolean isDel;

  private String bizNum;

  private String accountNumber;

  private String accessToken;
  private String refreshToken;

  public MemberDTO(MemberEntity member) {
    this.mno = member.getMno();
    this.memberId = member.getMemberId();
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
        .memberId(this.memberId)
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

  public Map<String, Object> getDataMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("mno", mno);
    map.put("memberId", memberId);
    map.put("role", role);
    map.put("email", email);
    map.put("nickname", nickname);

    return map;
  }
}
