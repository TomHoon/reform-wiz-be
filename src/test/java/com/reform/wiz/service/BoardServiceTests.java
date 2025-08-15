package com.reform.wiz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.reform.wiz.dto.BoardDTO;
import com.reform.wiz.dto.PageRequestDTO;
import com.reform.wiz.dto.PageResponseDTO;
import com.reform.wiz.entity.File;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  /*
   * [테스트케이스]
   * 📌 CREATE
   * 📌 READ (단건)
   * 📌 READ (전체)
   * 📌 READ (페이지)
   * 📌 UPDATE
   * 📌 DELETE
   */

  // ✅ CREATE
  @Test
  @Commit
  public void testInsert() {
    // ⚠️ 100개씩 insert
    // ⚠️ 회원이 없는 경우 Insert 안됨

    File file = new File();
    file.setFileName("test filename");
    file.setFileUrl("test url");
    List<File> files = new ArrayList<>();
    files.add(file);

    for (int i = 0; i < 100; i++) {
      BoardDTO dto = new BoardDTO();
      dto.setTitle("Test Title " + i);
      dto.setBrand("BrandX" + i);
      dto.setContent("This is test content.");
      dto.setUsedPeriod(i + " year");
      dto.setWishDate(LocalDate.of(2025, 7, 1));
      dto.setWishPlace("Seoul");
      dto.setCreatedAt(LocalDateTime.now());
      dto.setUpdatedAt(LocalDateTime.now());
      dto.setIsDel(false);
      dto.setMemberId(1L); // test용으로 1L
      dto.setFiles(files);
      BoardDTO resultDTO = boardService.register(dto);
      log.info("resultDTO >>>>> " + resultDTO);
    }
  }

  // ✅ READ (단건)
  @Test
  public void testRead() {
    Long bno = 101L;

    BoardDTO dto = boardService.getOne(bno);

    log.info("dto >>>> {} ", dto);
  }

  // ✅ READ (페이지)
  @Test
  public void testReadPage() {
    int pageNum = 0; // 페이지 클릭시 param 받아서 처리
    int size = 10; // 고정

    Pageable pageable = PageRequest.of(pageNum, size, Sort.by("bno"));
    Map<String, Object> map = new HashMap<>();
    map.put("title", "test");
    map.put("content", "test");
    PageResponseDTO<BoardDTO> result = boardService.getAllByPage(pageable, map);

    result.getDtoList().stream().forEach(e -> log.info(">>> list item : {} ", e));

    log.info(">>>>> totalPage : {} ", result.getTotalPages());
    log.info(">>>>> TotalElements : {} ", result.getTotalElements());

  }

  // ✅ READ (전체)
  // 글 전체조회
  @Test
  public void 글전체조회() {
    List<BoardDTO> list = boardService.getAll();
    log.info(">>>>>>> list " + list.toString());
  }

  // ✅ UPDATE
  // 수정
  @Test
  public void 수정() {
    Long bno = 1L;

    BoardDTO 수정전dto = boardService.getOne(bno);
    boardService.updateBoard(수정전dto);
    수정전dto.setContent("수정했어요");

    log.info("수정 전 : {}", 수정전dto);

    BoardDTO 수정후 = boardService.getOne(bno);
    log.info("수정 후 : {}", 수정후);
  }

  @Test
  @Commit
  public void 삭제() {
    // 마지막 글 찾아서 삭제 테스트
    List<BoardDTO> list = boardService.getAll();
    BoardDTO dto = list.get(list.size() - 1);
    log.info("dto bno : {} ", dto.getBno());
    log.info("before dto : {} ", dto.getIsDel());
    dto.setIsDel(true);

    boardService.updateBoard(dto);
    log.info("after dto : {} ", dto.getIsDel());
  }

  @Test
  public void 유저페이징글조회() {
    Pageable page = PageRequestDTO.builder().page(1).build().getPageable(Sort.by("bno"));
    PageResponseDTO<BoardDTO> res = boardService.getBoardByMemberId("loginUser", page);

    log.info(">>>>getMemberId {} ", res.getDtoList().get(0).getMemberId());

    assertThat(res.getDtoList())
        .isNotEmpty()
        .allSatisfy(
            dto -> assertThat(dto).isInstanceOf(BoardDTO.class));

  }
}
