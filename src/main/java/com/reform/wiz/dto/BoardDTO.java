package com.reform.wiz.dto;

import java.time.LocalDate;

import com.reform.wiz.entity.BoardEntity;
import com.reform.wiz.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

  private Long bno;

  private String title;

  private String brand;

  private String content;

  private String usedPeriod;

  private LocalDate wishDate;

  private String wishPlace;

  private LocalDate createdAt;

  private LocalDate updatedAt;

  private Boolean isDel = false;

  private Long memberId;

  public BoardDTO(BoardEntity entity) {
    this.bno = entity.getBno();
    this.title = entity.getTitle();
    this.brand = entity.getBrand();
    this.content = entity.getContent();
    this.usedPeriod = entity.getUsedPeriod();
    this.wishDate = entity.getWishDate();
    this.wishPlace = entity.getWishPlace();
    this.createdAt = entity.getCreatedAt();
    this.updatedAt = entity.getUpdatedAt();
    this.isDel = entity.getIsDel();
    this.memberId = entity.getMemberEntity().getMno();
  }

  public BoardEntity toEntity(BoardDTO dto, MemberEntity me) {
    return BoardEntity.builder()
        .title(dto.getTitle())
        .brand(dto.getBrand())
        .content(dto.getContent())
        .usedPeriod(dto.getUsedPeriod())
        .wishDate(dto.getWishDate())
        .wishPlace(dto.getWishPlace())
        .createdAt(dto.getCreatedAt())
        .updatedAt(dto.getUpdatedAt())
        .isDel(dto.getIsDel() != null ? dto.getIsDel() : false)
        .memberEntity(me)
        .build();
  }

}
