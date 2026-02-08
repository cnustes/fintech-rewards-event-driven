package com.camilonustes.fintech.rewards.application;

import com.camilonustes.fintech.rewards.domain.ProcessedEvent;
import com.camilonustes.fintech.rewards.domain.UserPoints;
import com.camilonustes.fintech.rewards.event.PaymentConfirmedEvent;
import com.camilonustes.fintech.rewards.infrastructure.ProcessedEventRepository;
import com.camilonustes.fintech.rewards.infrastructure.UserPointsRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardsServiceImpl implements RewardsService {

    private final ProcessedEventRepository eventRepository;
    private final UserPointsRepository pointsRepository;
    private final MeterRegistry meterRegistry;

    @Override
    @Transactional
    public void processPaymentEvent(PaymentConfirmedEvent event) {
        // 1. Idempotency Check (Idempotent Consumer Pattern)
        if (eventRepository.existsById(event.transactionId())) {
            log.warn("SKIPPING: Event {} already processed.", event.transactionId());
            return;
        }

        // 2. Mark event as processed
        eventRepository.save(new ProcessedEvent(event.transactionId(), LocalDateTime.now()));

        // 3. Business Logic: 1 point per USD
        long pointsToAdd = event.amount().longValue();

        UserPoints userPoints = pointsRepository.findById(event.userId())
                .orElse(UserPoints.builder()
                        .userId(event.userId())
                        .totalPoints(0L)
                        .build());

        userPoints.setTotalPoints(userPoints.getTotalPoints() + pointsToAdd);
        pointsRepository.save(userPoints);

        // 4. Business Metrics (Micrometer Counter)
        Counter.builder("fintech.rewards.points.total")
                .description("Total amount of reward points granted")
                .tag("service", "rewards-service")
                .register(meterRegistry)
                .increment(pointsToAdd);

        log.info("SUCCESS: User {} rewarded with {} points. New balance: {}",
                event.userId(), pointsToAdd, userPoints.getTotalPoints());
    }
}
