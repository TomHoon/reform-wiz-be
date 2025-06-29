package com.reform.wiz.service;

import com.reform.wiz.dto.PaymentDTO;
import com.reform.wiz.entity.PaymentEntity;
import com.reform.wiz.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제 승인 (Create)
    @Transactional
    public PaymentDTO approvePayment(PaymentDTO dto) {
        log.info("결제 승인 요청: {}", dto);
        PaymentEntity entity = dto.toEntity();
        PaymentEntity saved = paymentRepository.save(entity);
        return new PaymentDTO(saved);
    }

    // 결제 전체 조회 (Read)
    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments() {
        List<PaymentEntity> result = paymentRepository.findAll();
        return result.stream().map(PaymentDTO::new).collect(Collectors.toList());
    }

    // 결제 취소 (Update)
    @Transactional
    public PaymentDTO cancelPayment(Long pno) {
        PaymentEntity entity = paymentRepository.findById(pno).orElseThrow(
                () -> new IllegalArgumentException("해당 결제 내역이 존재하지 않습니다. pno=" + pno));
        entity.cancel(); // canceled_at 갱신
        return new PaymentDTO(entity);
    }
}
