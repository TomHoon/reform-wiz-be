package com.reform.wiz.controller;

import com.reform.wiz.dto.PaymentDTO;
import com.reform.wiz.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@Log4j2
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 전체 조회
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> list = paymentService.getAllPayments();
        return ResponseEntity.ok(list);
    }

    // 결제 승인
    @PostMapping("/approve")
    public ResponseEntity<PaymentDTO> approvePayment(@RequestBody PaymentDTO dto) {
        PaymentDTO saved = paymentService.approvePayment(dto);
        return ResponseEntity.ok(saved);
    }

    // 결제 취소
    @PutMapping("/cancel")
    public ResponseEntity<PaymentDTO> cancelPayment(@RequestParam Long pno) {
        PaymentDTO canceled = paymentService.cancelPayment(pno);
        return ResponseEntity.ok(canceled);
    }
}
