package com.reform.wiz.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실데이터 사용
@Transactional(propagation = org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED)
public class BoardRepositoryTests {

  @Autowired
  private BoardRepository boardRepository;

  @Test
  @Commit
  public void testInsert() {
  }
}
