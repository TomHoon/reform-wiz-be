package com.reform.wiz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reform.wiz.dto.BidDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BidServiceTests {
  

  @Autowired
  private BidService bidService;

  /*
   * [테스트케이스]
   * 📌 CREATE
   * 📌 READ (게시글에 달린 입찰리스트)
   * 📌 UPDATE
   * 📌 DELETE
   */

  @Test
  public void insert() {
    BidDTO dto = BidDTO.builder()
        .bidPrice(10000)
        .isBid(false)
        .build();

    Boolean isSaved = bidService.requestBid(1L, 1L, dto);
    log.info("isSaved >>> {} ", isSaved);
  }
  
  @Test
  public void read() {
    List<BidDTO> list = bidService.getBidList(1L);

    assertThat(list).isNotEmpty().hasSize(1);
  }
  
  @Test
  public void cancel() {
    Long bidNo = 1L;
    Boolean isCanceled = bidService.cancelBid(bidNo);
    assertThat(isCanceled).isTrue();
  }

  @Test
  public void modify() {
    Long bidNo = 2L;
    BidDTO dto = BidDTO.builder()
    .bidPrice(20000)
    .isBid(false)
        .build();

    BidDTO changedDTO = bidService.modify(bidNo, dto);
    int changedBidPrice = changedDTO.getBidPrice();

    assertThat(changedBidPrice).isEqualTo(20000);
  }
}
