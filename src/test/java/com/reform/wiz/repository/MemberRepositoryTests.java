package com.reform.wiz.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.reform.wiz.entity.MemberEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실데이터 사용
@Transactional(propagation = org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED)
public class MemberRepositoryTests {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @Commit
  public void testInsert() {
    // ✅ CREATE
    MemberEntity e = MemberEntity.builder()
        .id("user123")
        .password("secret")
        .name("Tom")
        .nickname("tommy")
        .phone("010-1234-5678")
        .email("tom@example.com")
        .isCompany(false)
        .bizNum("123-45-67890")
        .accountNumber("111-222-333")
        .build();

    memberRepository.save(e);
  }

  @Test
  public void testRead() {
    // ✅ READ
    Optional<MemberEntity> res = memberRepository.findById(1L);

    res.ifPresent(e -> System.out.println("e >>> " + e));
  }

  @Test
  public void testWithoutTransactional() {
    // ✅ READ
    // case: 트랜잭션 미사용 케이스
    Long mno = 1L;

    Optional<MemberEntity> res1 = memberRepository.findById(mno);
    System.out.println("res1 >>>>>> " + res1);

    Optional<MemberEntity> res2 = memberRepository.findById(mno);
    System.out.println("res2 >>>>>> " + res2);
  }

  @Test
  @Transactional
  public void testWithTransactional() {
    // ✅ READ
    // case: 트랜잭션 사용 케이스
    // -> 영속성 콘텍스트에 포함되기 때문에 같은 내용일 경우 조회 1번

    Long mno = 1L;

    Optional<MemberEntity> res1 = memberRepository.findById(mno);
    System.out.println("res1 >>>>>> " + res1);

    Optional<MemberEntity> res2 = memberRepository.findById(mno);
    System.out.println("res2 >>>>>> " + res2);
  }

  @Test
  @Transactional
  public void testUpdateWithDirtyChecking() {
    // ✅ UPDATE(dirty check)

    // case: 트랜잭션 사용하여 수정케이스
    // -> DirtyChecking

    Long mno = 1L;
    Optional<MemberEntity> 수정전 = memberRepository.findById(mno);
    수정전.ifPresent(e -> System.out.println("e >>>> " + e));

    MemberEntity m = 수정전.get();

    m.changeNickname("test tomhoon");

    Optional<MemberEntity> 수정후 = memberRepository.findById(mno);
    수정후.ifPresent(e -> System.out.println("e >>>> " + e));

  }

  @Test
  @Commit
  public void testUpdateWithoutDirtyChecking() {
    // ✅ UPDATE(without dirty check)

    Long mno = 1L;

    Optional<MemberEntity> e = memberRepository.findById(mno);

    e.ifPresent(ele -> System.out.println("ele >>>> " + ele));

    MemberEntity m = e.get();
    m.changeNickname("tomhoon Lee");

    memberRepository.save(m);
  }

  @Test
  @Commit
  public void testDeleteWithDirtyChecking() {
    // ✅ Delete(dirty check)
    Long mno = 1L;

    memberRepository.deleteById(mno);
  }
}
