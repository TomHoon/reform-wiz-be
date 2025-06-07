package com.reform.wiz.service;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.reform.wiz.dto.BoardDTO;
import com.reform.wiz.dto.PageResponseDTO;
import com.reform.wiz.entity.BoardEntity;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  @Test
  @Commit
  public void testInsert() {
    // ✅ CREATE
    BoardDTO dto = new BoardDTO();
    dto.setTitle("Test Title");
    dto.setBrand("BrandX");
    dto.setContent("This is test content.");
    dto.setUsedPeriod("1 year");
    dto.setWishDate(LocalDate.of(2025, 7, 1));
    dto.setWishPlace("Seoul");
    dto.setCreatedAt(LocalDate.now());
    dto.setUpdatedAt(LocalDate.now());
    dto.setIsDel(false);

    BoardDTO resultDTO = boardService.register(dto);

    log.info("resultDTO >>>>> " + resultDTO);
  }

  @Test
  public void testRead() {
    // ✅ READ
    Long bno = 1L;

    BoardDTO dto = boardService.getOne(bno);

    log.info("dto >>>> " + dto);
  }

  @Test
  public void testReadPage() {
    // ✅ READ by Page
    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno"));
    PageResponseDTO<BoardDTO> result = boardService.getAllByPage(pageable);

    log.info(">>>>> getDtoList " + result.getDtoList());
    log.info(">>>>> getTotalPages" + result.getTotalPages());
    log.info(">>>>> getTotalElements " + result.getTotalElements());

  }
}
