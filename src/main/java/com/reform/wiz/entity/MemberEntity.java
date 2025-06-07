package com.reform.wiz.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_member")
@ToString
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;

  @Column(unique = true)
  private String id;

  private String password;

  private String name;

  @Column(unique = true)
  private String nickname;

  @Column(unique = true)
  private String phone;

  private String email;

  private Boolean isCompany;

  @CreatedDate
  private LocalDate createdAt;

  @Builder.Default
  private Boolean isDel = false;

  @Column(unique = true)
  private String bizNum;

  private String accountNumber;

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

}
