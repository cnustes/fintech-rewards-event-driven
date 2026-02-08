package com.camilonustes.fintech.payment.infrastructure;
import com.camilonustes.fintech.payment.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {}
