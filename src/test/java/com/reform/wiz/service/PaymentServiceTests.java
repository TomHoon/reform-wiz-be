package com.reform.wiz.service;

import com.reform.wiz.dto.PaymentDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
@Log4j2
public class PaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    /*
     * [테스트 케이스]
     * 📌 READ - 결제 조회
     * 📌 CREATE - 결제 승인
     * 📌 UPDATE - 결제 취소
     */

    // ✅ READ
    @Test
    @Commit
    public void testApprove() {
        PaymentDTO dto = new PaymentDTO();
        dto.setBno(1);
        dto.setAmount(100000);
        dto.setDetail("서비스 테스트 결제");
        dto.setType("CARD");

        PaymentDTO saved = paymentService.approvePayment(dto);
        log.info("Saved >>> {}", saved);
    }

    // ✅ CREATE
    @Test
    public void testGetAll() {
        List<PaymentDTO> list = paymentService.getAllPayments();
        log.info("All Payments >>> {}", list);
    }

    // ✅ UPDATE
    @Test
    @Commit
    public void testCancel() {
        Long pno = 1L; // 테스트용
        PaymentDTO result = paymentService.cancelPayment(pno);
        log.info("Canceled >>> {}", result);
    }
}
