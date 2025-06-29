package com.reform.wiz.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.reform.wiz.entity.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long cno;

    private String categoryName;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ItemDTO> items;

    public CategoryDTO(CategoryEntity entity) {
        this.cno = entity.getCno();
        this.categoryName = entity.getCategoryName();
        this.description = entity.getDescription();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();

        // 하위 품목 매핑
        if (entity.getItems() != null) {
            this.items = entity.getItems().stream()
                    .map(ItemDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
