package com.reform.wiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "payment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    @Column(nullable = false)
    private Integer bno; // 게시글 번호 (board.bno 참조)

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String type;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    public void cancel() {
        this.canceledAt = LocalDateTime.now();
    }

    public void changeAmount(Integer amount) {
        this.amount = amount;
    }

    public void changeDetail(String detail) {
        this.detail = detail;
    }

    public void changeType(String type) {
        this.type = type;
    }

    // TODO: 추후 bidNo가 외래키로 들어오면 아래처럼 연관관계 매핑할 예정
    // @ManyToOne
    // @JoinColumn(name = "bidNo")
    // private BidEntity bid;
}
