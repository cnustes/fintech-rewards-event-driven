package com.camilonustes.fintech.rewards.application;

import com.camilonustes.fintech.rewards.event.PaymentConfirmedEvent;

public interface RewardsService {
    void processPaymentEvent(PaymentConfirmedEvent event);
}
