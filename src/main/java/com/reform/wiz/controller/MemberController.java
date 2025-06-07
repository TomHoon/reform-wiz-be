package com.reform.wiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reform.wiz.dto.MemberDTO;
import com.reform.wiz.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

  @GetMapping("/join")
  public ResponseEntity<ApiResponse<MemberDTO>> join(@RequestBody MemberDTO dto) {
    return ResponseEntity.ok(ApiResponse.success(dto));
  }

}
