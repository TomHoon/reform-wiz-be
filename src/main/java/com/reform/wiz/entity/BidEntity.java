package com.reform.wiz.entity;

import com.reform.wiz.dto.BidDTO;

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
import lombok.extern.log4j.Log4j2;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Builder
@Table(name = "tbl_bid")
public class BidEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;

  @Builder.Default
  private int bidPrice = 0;

  @Builder.Default
  private Boolean isBid = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_bno")
  private BoardEntity boardEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_mno")
  private MemberEntity memberEntity;

  public void update(BidDTO dto) {
    this.bidPrice = dto.getBidPrice();
    this.isBid = dto.getIsBid();
  }

}
