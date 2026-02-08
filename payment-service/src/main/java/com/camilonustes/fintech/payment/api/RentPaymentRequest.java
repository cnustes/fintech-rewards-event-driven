package com.camilonustes.fintech.payment.api;

import java.math.BigDecimal;

public record RentPaymentRequest(
        String userId,
        BigDecimal amount,
        String propertyId,
        String paymentMethod
) {}