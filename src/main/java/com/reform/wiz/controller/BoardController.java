package com.reform.wiz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
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

  @GetMapping("/getAll")
  public ResponseEntity<ApiResponse<List<BoardDTO>>> getAll() {
    // 글 전체조회
    List<BoardDTO> dto = boardService.getAll();

    return ResponseEntity.ok(ApiResponse.success(dto));
  }

  @GetMapping("/{bno}")
  public ResponseEntity<ApiResponse<BoardDTO>> getBoard(@PathVariable(name = "bno") Long bno) {
    // 글 단건조회
    BoardDTO dto = boardService.getOne(bno);
    return ResponseEntity.ok(ApiResponse.success(dto));
  }

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

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<BoardDTO>> registerBoard(@RequestBody BoardDTO dto) {
    // 글 등록
    BoardDTO resultDTO = boardService.register(dto);
    return ResponseEntity.ok(ApiResponse.success(resultDTO));
  }

  @DeleteMapping("/{bno}")
  public ResponseEntity<ApiResponse<BoardDTO>> deleteBoard(@PathVariable Long bno) {
    BoardDTO dto = boardService.deleteBoard(bno);
    return ResponseEntity.ok(ApiResponse.success(dto));
  }

  @PutMapping("/update")
  public ResponseEntity<ApiResponse<BoardDTO>> updateBoard(@RequestBody BoardDTO dto) {
    BoardDTO resultDTO = boardService.updateBoard(dto);

    return ResponseEntity.ok(ApiResponse.success(resultDTO));
  }

}
