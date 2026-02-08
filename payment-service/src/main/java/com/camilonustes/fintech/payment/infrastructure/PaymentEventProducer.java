package com.camilonustes.fintech.payment.infrastructure;

import com.camilonustes.fintech.payment.domain.PaymentConfirmedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentConfirmedEvent> kafkaTemplate;
    private static final String TOPIC = "payment-confirmed";

    public PaymentEventProducer(KafkaTemplate<String, PaymentConfirmedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(PaymentConfirmedEvent event) {
        System.out.println("Publishing event to Kafka: " + event.transactionId());
        kafkaTemplate.send(TOPIC, event.userId(), event);
    }
}