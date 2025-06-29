package com.reform.wiz.dto;

import com.reform.wiz.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long ino;

    private String type;

    public ItemDTO(ItemEntity entity) {
        this.ino = entity.getIno();
        this.type = entity.getType();
    }
}
