package com.reform.wiz.dto;

import com.reform.wiz.entity.BidEntity;
import com.reform.wiz.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class BidDTO {

  private Long bno;

  private int bidPrice;

  private Boolean isBid;

  private BoardDTO bDTO;

  public BidDTO(BidEntity bidEntity) {
    BoardDTO boardDTO = new BoardDTO(bidEntity.getBoardEntity());

    this.bno = bidEntity.getBno();
    this.bidPrice = bidEntity.getBidPrice();
    this.isBid = bidEntity.getIsBid();

    this.bDTO = boardDTO;
  }

}
