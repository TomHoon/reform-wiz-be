package com.reform.wiz.controller;

import com.reform.wiz.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
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

import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final JwtUtil jwtUtil;

  // 회원가입
  @PostMapping("/join")
  public ResponseEntity<ApiResponse<MemberDTO>> join(@RequestBody MemberDTO dto) {
    MemberDTO result = memberService.join(dto);
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 로그아웃
  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Map<String, Object>>> logout(HttpServletResponse response) {

    ResponseCookie cookie = ResponseCookie
            .from("accessToken", "")
            .secure(true)  // true in production
            .httpOnly(true)
            .path("/")
            .maxAge(0)      // delete immediately
            .build();

    response.addHeader("Set-Cookie", cookie.toString());

    return ResponseEntity.ok(ApiResponse.success(Map.of("result", "success")));

  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<MemberDTO>> login(@RequestBody MemberDTO dto, HttpServletResponse response) {
    MemberDTO result = memberService.login(dto.getMemberId(), dto.getPassword());

    String accessToken = jwtUtil.createToken(result.getDataMap(), 10);
    String refreshToken = jwtUtil.createToken(Map.of("mid", result.getMemberId()), 60);

    result.setAccessToken(accessToken);
    result.setRefreshToken(refreshToken);

    /*Cookie cookie = new Cookie("access_token", accessToken);
    cookie.setHttpOnly(true); // 🚨 cannot be accessed by JS
    cookie.setSecure(false);   // only over HTTPS
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60); // 1 hour
    response.addCookie(cookie);*/

    ResponseCookie cookie = ResponseCookie
            .from("accessToken",accessToken)
            .domain("localhost")
            .maxAge(60)
            .sameSite("None")
            .secure(true)
            .httpOnly(true)
            .path("/")
            .build();

    response.addHeader("Set-Cookie", cookie.toString());

    return ResponseEntity.ok(ApiResponse.success(result));
  }

  // 아이디/비밀번호 찾기
  @PostMapping("/find")
  public ResponseEntity<ApiResponse<MemberDTO>> find(@RequestBody MemberDTO dto) {
    MemberDTO result = memberService.findByIdOrPw(dto.getPhone());
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