package com.camilonustes.fintech.rewards.infrastructure;

import com.camilonustes.fintech.rewards.application.RewardsService;
import com.camilonustes.fintech.rewards.event.PaymentConfirmedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final RewardsService rewardsService;

    @KafkaListener(topics = "payment-confirmed", groupId = "rewards-group")
    public void handlePaymentConfirmed(PaymentConfirmedEvent event) {
        log.info("Kafka: Received payment confirmed event for user {} (TX: {})",
                event.userId(), event.transactionId());

        // Delegating all business logic and metrics to the application layer
        rewardsService.processPaymentEvent(event);
    }
}