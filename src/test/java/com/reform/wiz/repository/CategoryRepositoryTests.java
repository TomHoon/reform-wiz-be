package com.reform.wiz.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.reform.wiz.entity.CategoryEntity;

import lombok.extern.log4j.Log4j2;

@ActiveProfiles("test")
@DataJpaTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ 전체 카테고리 조회
    @Test
    public void 카테고리전체조회() {
        System.out.println("✅ 카테고리전체조회 테스트가 실행되었습니다.");

        List<CategoryEntity> list = categoryRepository.findAll();
        log.info("전체 카테고리 수: {}", list.size());
        System.out.println("카테고리 개수: " + list.size());

        for (CategoryEntity c : list) {
            log.info("카테고리: {}", c.getCategoryName());
            c.getItems().forEach(item -> log.info(" └ 품목: {}", item.getType()));
        }
    }

    // ✅ 단일 카테고리 조회
    @Test
    public void 카테고리단건조회() {
        Long cno = 1L; // 테스트 데이터가 있다고 가정
        CategoryEntity entity = categoryRepository.findById(cno).orElse(null);

        if (entity != null) {
            log.info("조회된 카테고리: {}", entity.getCategoryName());
            entity.getItems().forEach(item -> log.info(" └ 품목: {}", item.getType()));
        } else {
            log.warn("카테고리 ID {}를 찾을 수 없습니다.", cno);
        }
    }
}
