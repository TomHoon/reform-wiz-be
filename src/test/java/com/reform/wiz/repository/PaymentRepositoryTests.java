package com.reform.wiz.repository;

import com.reform.wiz.entity.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PaymentRepositoryTests {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @Commit
    public void testInsert() {
        PaymentEntity entity = PaymentEntity.builder()
                .bno(1)
                .amount(50000)
                .detail("테스트 결제")
                .type("CARD")
                .build();

        paymentRepository.save(entity);
    }

    @Test
    public void testRead() {
        Long pno = 1L;
        Optional<PaymentEntity> result = paymentRepository.findById(pno);
        result.ifPresent(e -> System.out.println("결제 정보: " + e));
    }
}
