package com.camilonustes.fintech.payment.api;

import com.camilonustes.fintech.payment.application.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    // 1. Declare dependency (immutable)
    private final PaymentService paymentService;

    // 2. Constructor Injection (Senior best practice)
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/rent")
    public ResponseEntity<PaymentResponse> processRent(@RequestBody RentPaymentRequest request) {
        // Log to verify request reached controller
        System.out.println("Controller: Received payment request for user " + request.userId());

        // 3. HERE IS THE TRICK! Call the service
        // This will trigger the breakpoint in the debugger
        PaymentResponse response = paymentService.processPayment(request);

        return ResponseEntity.ok(response);
    }
}