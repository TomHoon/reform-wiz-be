package com.reform.wiz.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reform.wiz.dto.CategoryDTO;
import com.reform.wiz.service.CategoryService;
import com.reform.wiz.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 조회
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> dto = categoryService.getAll();
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    // 카테고리 상세조회
    @GetMapping("/detail/{cno}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryDetail(@PathVariable("cno") Long cno) {
        CategoryDTO dto = categoryService.getOne(cno);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

}
