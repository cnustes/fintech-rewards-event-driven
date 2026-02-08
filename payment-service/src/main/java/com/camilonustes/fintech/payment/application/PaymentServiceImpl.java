package com.camilonustes.fintech.payment.application;

import com.camilonustes.fintech.payment.api.PaymentResponse;
import com.camilonustes.fintech.payment.api.RentPaymentRequest;
import com.camilonustes.fintech.payment.domain.PaymentConfirmedEvent;
import com.camilonustes.fintech.payment.domain.PaymentEntity;
import com.camilonustes.fintech.payment.infrastructure.PaymentEventProducer;
import com.camilonustes.fintech.payment.infrastructure.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentEventProducer eventProducer;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentEventProducer eventProducer, PaymentRepository paymentRepository) {
        this.eventProducer = eventProducer;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional // Vital for Fintech data integrity!
    public PaymentResponse processPayment(RentPaymentRequest request) {
        String txId = UUID.randomUUID().toString();

        // 1. Database Persistence
        PaymentEntity payment = PaymentEntity.builder()
                .id(txId)
                .userId(request.userId())
                .amount(request.amount())
                .status("PROCESSED")
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        // 2. Send to Kafka
        PaymentConfirmedEvent event = new PaymentConfirmedEvent(
                txId, request.userId(), request.amount());

        eventProducer.sendPaymentEvent(event);

        return new PaymentResponse(txId, "SUCCESS", "Event sent to Kafka");
    }
}