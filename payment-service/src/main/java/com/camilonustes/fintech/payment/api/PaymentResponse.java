package com.camilonustes.fintech.payment.api;

public record PaymentResponse(
        String transactionId,
        String status,
        String message
) {}