package com.reform.wiz.dto;

import com.reform.wiz.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long pno;

    private Integer bno; // 게시글 번호

    private Integer amount;

    private String detail;

    private String type;

    private LocalDateTime createdAt;

    private LocalDateTime canceledAt;

    public PaymentDTO(PaymentEntity entity) {
        this.pno = entity.getPno();
        this.bno = entity.getBno();
        this.amount = entity.getAmount();
        this.detail = entity.getDetail();
        this.type = entity.getType();
        this.createdAt = entity.getCreatedAt();
        this.canceledAt = entity.getCanceledAt();
    }

    public PaymentEntity toEntity() {
        return PaymentEntity.builder()
                .pno(this.pno)
                .bno(this.bno)
                .amount(this.amount)
                .detail(this.detail)
                .type(this.type)
                .createdAt(this.createdAt != null ? this.createdAt : LocalDateTime.now())
                .canceledAt(this.canceledAt)
                .build();
    }
}
