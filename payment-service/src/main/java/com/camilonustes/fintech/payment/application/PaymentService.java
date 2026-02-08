package com.camilonustes.fintech.payment.application;

import com.camilonustes.fintech.payment.api.PaymentResponse;
import com.camilonustes.fintech.payment.api.RentPaymentRequest;

public interface PaymentService {
    PaymentResponse processPayment(RentPaymentRequest request);
}