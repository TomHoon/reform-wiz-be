package com.reform.wiz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reform.wiz.dto.BidDTO;
import com.reform.wiz.service.BidService;
import com.reform.wiz.service.BoardService;
import com.reform.wiz.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bid")
@RequiredArgsConstructor
public class BidController {

  private final BidService bidService;

  // 조회, 요청, 수정,

  /**
   * ✅ 조회
   * 게시글에 묶인 입찰들 리스트 조회
   */
  @GetMapping("/getBidList/{boardNo}")
  public ResponseEntity<ApiResponse<List<BidDTO>>> getBidList(@PathVariable Long boardNo) {
    List<BidDTO> list = bidService.getBidList(boardNo);

    return ResponseEntity.ok(ApiResponse.success(list));
  }

  /**
   * ✅ 요청
   * 게시글 대상으로 입찰요청
   */
  @PostMapping("/request/{boardno}")
  public ResponseEntity<ApiResponse<Map<String, Boolean>>> requestBid(@PathVariable Long boardno, Long mno,
      BidDTO dto) {
    Boolean isSuccess = bidService.requestBid(boardno, mno, dto);

    return ResponseEntity.ok(ApiResponse.success(Map.of("isSuccess", isSuccess)));
  }

  /**
   * ✅ 수정
   * 입찰한 내용
   */
  @PostMapping("/modify/{bidno}")
  public ResponseEntity<ApiResponse<BidDTO>> modify(@PathVariable Long bidno, @RequestBody BidDTO dto) {
    BidDTO resultDTO = bidService.modify(bidno, dto);
    return ResponseEntity.ok(ApiResponse.success(resultDTO));
  }

  /**
   * ✅ 삭제
   * 입찰요청 삭제
   */
  @PostMapping("/cancel/{bidno}")
  public ResponseEntity<ApiResponse<Map<String, Boolean>>> cancelBid(@PathVariable Long bidno) {
    Boolean isDel = bidService.cancelBid(bidno);

    return ResponseEntity.ok(ApiResponse.success(Map.of("isDel", isDel)));
  }
}
