package com.reform.wiz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reform.wiz.dto.CategoryDTO;
import com.reform.wiz.entity.CategoryEntity;
import com.reform.wiz.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 전체 카테고리 조회
    public List<CategoryDTO> getAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    // 카테고리 상세조회
    public CategoryDTO getOne(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("카테고리 ID " + id + " 없음"));

        return new CategoryDTO(entity);
    }
}
