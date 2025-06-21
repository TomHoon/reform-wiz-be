package com.reform.wiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reform.wiz.dto.MemberDTO;
import com.reform.wiz.service.MemberService;
import com.reform.wiz.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  // 회원가입
  @PostMapping("/join")
  public ResponseEntity<ApiResponse<MemberDTO>> join(@RequestBody MemberDTO dto) {
    MemberDTO result = memberService.join(dto);
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<MemberDTO>> login(@RequestBody MemberDTO dto) {
    MemberDTO result = memberService.login(dto.getId(), dto.getPassword());
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 아이디/비밀번호 찾기 (이메일 기반 예시)
  @PostMapping("/find")
  public ResponseEntity<ApiResponse<MemberDTO>> find(@RequestBody MemberDTO dto) {
    MemberDTO result = memberService.findByIdOrPw(dto.getEmail());
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 회원 탈퇴
  @PostMapping("/delete")
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestBody MemberDTO dto) {
    boolean result = memberService.deleteMember(dto.getMno());
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 프로필 조회
  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<MemberDTO>> getProfile(@RequestParam Long id) {
    MemberDTO result = memberService.getProfile(id);
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 프로필 수정
  @PutMapping("/profile")
  public ResponseEntity<ApiResponse<MemberDTO>> updateProfile(@RequestBody MemberDTO dto) {
    MemberDTO result = memberService.updateProfile(dto);
    return ResponseEntity.ok(ApiResponse.success(result));
  }
}