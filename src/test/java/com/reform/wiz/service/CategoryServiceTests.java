package com.reform.wiz.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reform.wiz.dto.CategoryDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    /*
     * [테스트케이스]
     * 📌 READ (단건) ✅ getOne(cno)
     * 📌 READ (전체) ✅ getAll()
     */

    // ✅ 카테고리 전체 조회
    @Test
    public void 카테고리전체조회() {
        List<CategoryDTO> list = categoryService.getAll();
        log.info("전체 카테고리 수: {}", list.size());

        for (CategoryDTO dto : list) {
            log.info("카테고리: {}", dto.getCategoryName());
            if (dto.getItems() != null) {
                dto.getItems().forEach(item -> log.info(" └ 품목: {}", item.getType()));
            }
        }
    }

    // ✅ 카테고리 상세 조회
    @Test
    public void 카테고리상세조회() {
        Long cno = 1L; // 테스트용 카테고리 ID
        CategoryDTO dto = categoryService.getOne(cno);
        log.info("카테고리 이름: {}", dto.getCategoryName());

        if (dto.getItems() != null) {
            dto.getItems().forEach(item -> log.info("하위 품목: {}", item.getType()));
        }
    }
}
