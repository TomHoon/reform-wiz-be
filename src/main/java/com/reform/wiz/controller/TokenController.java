package com.reform.wiz.controller;

import com.reform.wiz.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.bind.annotation.*;

import com.reform.wiz.dto.MemberDTO;
import com.reform.wiz.service.MemberService;
import com.reform.wiz.utils.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/token")
public class TokenController {

 private final MemberService memberService;
 private final JwtUtil jwtUtil;

 @PostMapping("/make")
 public ResponseEntity<ApiResponse<Map<String, String>>> make(@RequestBody MemberDTO dto) {
  MemberDTO dtoResult = memberService.login(dto.getMemberId(), dto.getPassword());

  Map<String, Object> dataMap = dtoResult.getDataMap();
  String accessToken = jwtUtil.createToken(dataMap, 10);
  String refreshToken = jwtUtil.createToken(Map.of("email", dtoResult.getEmail()), 10);

  return ResponseEntity.ok(ApiResponse.success(Map.of("accessToken", accessToken, "refreshToken", refreshToken)));
 }

 @PostMapping("/refresh")
 public ResponseEntity<ApiResponse<Map<String, Object>>> refresh(@RequestHeader("Authorization")String accessTokenStr,
                                                                 @RequestParam("refreshToken") String refreshToken,
                                                                 @RequestParam("mid") String mid) {
  if (accessTokenStr == null || !accessTokenStr.startsWith("Bearer")) {
   return handleException("No Access Token", 400);
  }

  if (refreshToken == null) {
   return handleException("No Refresh Token", 400);
  }

  if (mid == null) {
   return handleException("No Mid" , 400);
  }

  String accessToken = accessTokenStr.substring(7);
  try {
   jwtUtil.validateToken(accessToken);
   Map<String, Object> map = makeData(mid, accessToken, refreshToken);
   return ResponseEntity.ok(ApiResponse.success(map));
  } catch (ExpiredJwtException e) {
   Map<String, Object> newMap = makeNewToken(mid, refreshToken);
   return ResponseEntity.ok(ApiResponse.success(newMap));
  }
 }

 public ResponseEntity<ApiResponse<Map<String, Object>>> handleException(String msg, int status) {
  return ResponseEntity.ok(ApiResponse.success(Map.of("msg", msg, "status",status)));
 }

 public Map<String, Object> makeData(String mid, String accessToken, String refreshToken) {
  return Map.of("mid", mid, "accessToken", accessToken, "refreshToken", refreshToken);
 }

 public Map<String, Object> makeNewToken(String mid, String refreshToken) {

  Map<String, Object> claims = jwtUtil.validateToken(refreshToken);
  String memberIdFromRefreshToken = claims.get("memmberId").toString();

  MemberDTO dto = memberService.getByMemberId(mid);

  if (!dto.getMemberId().equals(memberIdFromRefreshToken)) {
   // 토큰아이디와같지 않으면
   throw new RuntimeException("Invalid Refresh Token host");
  }

  Map<String, Object> newClaims = dto.getDataMap();
  String newToken = jwtUtil.createToken(newClaims, 10);
  String newRefreshToken = jwtUtil.createToken(Map.of("mid", dto.getMemberId()), 30);

  return makeData(mid, newToken, newRefreshToken);




 }

}
