package com.reform.wiz.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.reform.wiz.dto.BoardDTO;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "memberEntity")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_board")
@Builder
public class BoardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;

  private String title;

  private String brand;

  @Column(nullable = false)
  private String content;

  private String usedPeriod;

  private LocalDate wishDate;

  private String wishPlace;

  @CreatedDate
  private LocalDate createdAt;

  @LastModifiedDate
  private LocalDate updatedAt;

  @Builder.Default
  @Column(name = "is_del", nullable = false)
  private Boolean isDel = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_mno")
  private MemberEntity memberEntity;

  @ElementCollection
  @CollectionTable(name = "file", joinColumns = @JoinColumn(name = "bno"))
  private List<File> files = new ArrayList<>();

  public void changeIsDel(Boolean isDel) {
    this.isDel = isDel;
  }

  public void updateBoard(BoardDTO dto) {
    this.title = dto.getTitle();
    this.brand = dto.getBrand();
    this.content = dto.getContent();
    this.usedPeriod = dto.getUsedPeriod();
    this.wishDate = dto.getWishDate();
    this.wishPlace = dto.getWishPlace();
    this.isDel = dto.getIsDel();
  }
}
