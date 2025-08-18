package com.reform.wiz.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reform.wiz.dto.MemberDTO;
import com.reform.wiz.entity.MemberEntity;
import com.reform.wiz.exception.MemberExceptions;
import com.reform.wiz.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원가입
  public MemberDTO join(MemberDTO dto) {
    String password = dto.getPassword();
    password = passwordEncoder.encode(password);
    dto.setPassword(password);

    MemberEntity entity = dto.toEntity();

    MemberEntity saved = memberRepository.save(entity);

    return new MemberDTO(saved);
  }

  // 로그인
  public MemberDTO login(String memberId, String password) {
    MemberEntity member = memberRepository.findByMemberId(memberId)
        .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

    Boolean isSame = passwordEncoder.matches(password, member.getPassword());

    if (!isSame) {
      throw MemberExceptions.BAD_CREDENTIALS.get();
    }

    return new MemberDTO(member);
  }

  // 아이디/비번 찾기
  public MemberDTO findByIdOrPw(String phone) {
    MemberEntity member = memberRepository.findByPhone(phone)
        .orElseThrow(() -> new EntityNotFoundException("해당 전화번호의 사용자를 찾을 수 없습니다."));

    return new MemberDTO(member);
  }

  // 회원 탈퇴
  public boolean deleteMember(Long memberId) {
    MemberEntity member = memberRepository.findById(memberId)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

    member.changeIsDel(true);
    memberRepository.save(member);
    return true;
  }

  // 프로필 조회
  public MemberDTO getProfile(Long memberId) {
    MemberEntity member = memberRepository.findById(memberId)
        .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

    return new MemberDTO(member);
  }

  // 프로필 수정
  public MemberDTO updateProfile(MemberDTO dto) {
    MemberEntity member = memberRepository.findById(dto.getMno())
        .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

    member.updateProfile(dto);

    MemberEntity updated = memberRepository.save(member);
    return new MemberDTO(updated);
  }

  public MemberDTO getByMemberId(String memberId) {
    MemberEntity e = memberRepository.findByMemberId(memberId).orElseThrow(MemberExceptions.NOT_FOUND::get);
    return new MemberDTO(e);
  }
}
