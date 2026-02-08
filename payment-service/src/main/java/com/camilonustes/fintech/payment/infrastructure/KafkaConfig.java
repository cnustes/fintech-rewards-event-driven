package com.camilonustes.fintech.payment.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Slf4j
public class KafkaConfig {

    public static final String TOPIC_PAYMENT_CONFIRMED = "payment-confirmed";

    @Bean
    public NewTopic paymentConfirmedTopic() {
        log.info("Provisioning Kafka Topic: {}", TOPIC_PAYMENT_CONFIRMED);
        return TopicBuilder.name(TOPIC_PAYMENT_CONFIRMED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
