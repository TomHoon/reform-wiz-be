package com.reform.wiz.service;

import com.reform.wiz.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    /*
     * [테스트 케이스]
     * 📌 LOGIN - 로그인
     * 📌 FIND - 아이디/비번 찾기
     * 📌 CREATE - 회원가입
     * 📌 READ - 회원 단건조회
     * 📌 UPDATE - 회원정보 수정
     * 📌 DELETE - 회원 탈퇴
     */

    // ✅ 로그인
    @Test
    public void testLogin() {
        // 먼저 회원 등록
        MemberDTO dto = new MemberDTO();
        dto.setId("loginUser");
        dto.setPassword("loginpw");
        dto.setName("로그인용");
        dto.setNickname("로그인닉");
        dto.setPhone("01055556666");
        dto.setEmail("login@example.com");
        dto.setIsCompany(false);

        memberService.join(dto);

        // 로그인 테스트
        MemberDTO loginResult = memberService.login("loginUser", "loginpw");
        assertThat(loginResult.getId()).isEqualTo("loginUser");

        log.info("로그인 성공: {}", loginResult);
    }

    // ✅ 아이디/비번 찾기 (휴대폰 기준)
    @Test
    public void testFindByPhone() {
        // 테스트용 회원 등록
        MemberDTO dto = new MemberDTO();
        dto.setId("findUser");
        dto.setPassword("findpw");
        dto.setName("찾기용");
        dto.setNickname("찾기닉");
        dto.setPhone("01099998888");
        dto.setEmail("find@example.com");
        dto.setIsCompany(false);

        memberService.join(dto);

        // 아이디/비번 찾기 테스트
        MemberDTO result = memberService.findByIdOrPw("01099998888");

        assertThat(result.getPhone()).isEqualTo("01099998888");
        log.info("찾은 사용자: {}", result);
    }

    // ✅ CREATE - 회원가입
    @Test
    @Commit
    public void testJoin() {
        MemberDTO dto = new MemberDTO();
        dto.setId("testUser123");
        dto.setPassword("password");
        dto.setName("테스터");
        dto.setNickname("tester123");
        dto.setPhone("01012345678");
        dto.setEmail("test@example.com");
        dto.setIsCompany(false);

        MemberDTO saved = memberService.join(dto);
        assertThat(saved.getId()).isEqualTo("testUser123");

        log.info("가입 완료: {}", saved);
    }

    // ✅ READ - 프로필 조회
    @Test
    public void testGetProfile() {
        Long memberId = 1L; // 사전 등록된 테스트 회원 ID
        MemberDTO profile = memberService.getProfile(memberId);

        assertThat(profile).isNotNull();
        log.info("조회된 프로필: {}", profile);
    }

    // ✅ UPDATE - 프로필 수정
    @Test
    @Commit
    public void testUpdateProfile() {
        Long memberId = 1L;
        MemberDTO dto = memberService.getProfile(memberId);
        dto.setNickname("updatedTester");
        dto.setPhone("01098765432");

        MemberDTO updated = memberService.updateProfile(dto);

        assertThat(updated.getNickname()).isEqualTo("updatedTester");
        log.info("수정 완료: {}", updated);
    }

    // ✅ DELETE - 회원 탈퇴
    @Test
    @Commit
    public void testDeleteMember() {
        Long memberId = 1L;

        boolean result = memberService.deleteMember(memberId);
        assertThat(result).isTrue();

        log.info("회원 탈퇴 처리 완료 for ID: {}", memberId);
    }

}
