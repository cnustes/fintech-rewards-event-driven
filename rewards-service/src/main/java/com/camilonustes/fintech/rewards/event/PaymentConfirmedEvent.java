package com.camilonustes.fintech.rewards.event;

import java.math.BigDecimal;

public record PaymentConfirmedEvent(
        String transactionId,
        String userId,
        BigDecimal amount
) {}