package com.reform.wiz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reform.wiz.dto.BoardDTO;
import com.reform.wiz.dto.PageRequestDTO;
import com.reform.wiz.dto.PageResponseDTO;
import com.reform.wiz.service.BoardService;
import com.reform.wiz.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  // 글 전체조회
  @GetMapping("/getAll")
  public ResponseEntity<ApiResponse<List<BoardDTO>>> getAll() {
    List<BoardDTO> dto = boardService.getAll();

    return ResponseEntity.ok(ApiResponse.success(dto));
  }

  // 글 단건조회
  @GetMapping("/{bno}")
  public ResponseEntity<ApiResponse<BoardDTO>> getBoard(@PathVariable(name = "bno") Long bno) {
    BoardDTO dto = boardService.getOne(bno);
    return ResponseEntity.ok(ApiResponse.success(dto));
  }

  // 글 페이징처리조회
  @GetMapping("/getBoards")
  public ResponseEntity<ApiResponse<PageResponseDTO<BoardDTO>>> getBoard(@RequestBody Map<String, String> param) {
    int page = Integer.parseInt(param.get("page"));
    int size = Integer.parseInt(param.get("size"));

    PageRequestDTO dto = PageRequestDTO.builder()
        .page(page)
        .size(size)
        .build();

    PageResponseDTO res = boardService.getAllByPage(dto.getPageable(Sort.by("bno")));
    return ResponseEntity.ok(ApiResponse.success(res));
  }

  // 글 등록
  @PostMapping("/register")
  public ResponseEntity<ApiResponse<BoardDTO>> registerBoard(@RequestBody BoardDTO dto) {
    // 글 등록
    BoardDTO resultDTO = boardService.register(dto);
    return ResponseEntity.ok(ApiResponse.success(resultDTO));
  }

  // 글 삭제
  @DeleteMapping("/{bno}")
  public ResponseEntity<ApiResponse<BoardDTO>> deleteBoard(@PathVariable Long bno) {
    BoardDTO dto = boardService.deleteBoard(bno);
    return ResponseEntity.ok(ApiResponse.success(dto));
  }

  // 글 수정
  @PutMapping("/update")
  public ResponseEntity<ApiResponse<BoardDTO>> updateBoard(@RequestBody BoardDTO dto) {
    BoardDTO resultDTO = boardService.updateBoard(dto);

    return ResponseEntity.ok(ApiResponse.success(resultDTO));
  }

}
