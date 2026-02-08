package com.camilonustes.fintech.payment.domain;

import java.math.BigDecimal;

public record PaymentConfirmedEvent(
        String transactionId,
        String userId,
        BigDecimal amount
) {}