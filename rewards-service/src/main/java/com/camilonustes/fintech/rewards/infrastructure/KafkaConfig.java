package com.camilonustes.fintech.rewards.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // Define the Dead Letter Publishing Recoverer
        // It will send failed messages to the original topic name + ".DLQ" by default
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (r, e) -> {
                    log.error(
                            "Message processing failed after retries. Sending to DLQ. Topic: {}, Partition: {}, Offset: {}",
                            r.topic(), r.partition(), r.offset(), e);
                    return new TopicPartition(r.topic() + ".DLQ", r.partition());
                });

        // Configure Error Handler with 3 retries, 1 second apart
        // FixedBackOff(interval, maxAttempts) - maxAttempts implies total tries
        // including the first one? No, it's retries.
        // Actually Spring docs say: maxAttempts in FixedBackOff is the number of
        // attempts (including the first one).
        // So for 3 retries (4 attempts total), we set it to 3L? Let's check.
        // If we want "3 retries", that means 1 initial + 3 retries = 4 attempts.
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3L));

        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}
