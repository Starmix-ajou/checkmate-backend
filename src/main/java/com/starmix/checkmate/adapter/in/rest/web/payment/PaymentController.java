package com.starmix.checkmate.adapter.in.rest.web.payment;

import com.starmix.checkmate.adapter.in.rest.web.payment.request.CompletePaymentRequest;
import com.starmix.checkmate.application.service.PaymentService;
import com.starmix.checkmate.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/complete")
    public Payment completePayment(@RequestBody CompletePaymentRequest request) {
        return paymentService.completePayment(request.paymentId());
    }

    @PostMapping("/webhook")
    public void webhook(
            @RequestBody String body,
            @RequestHeader("webhook-id") String id,
            @RequestHeader("webhook-timestamp") String timestamp,
            @RequestHeader("webhook-signature") String signature
    ) {
        paymentService.handleWebhook(body, id, timestamp, signature);
    }
}