package com.starmix.checkmate.application.port.out.payment;

import io.portone.sdk.server.payment.PaidPayment;
import io.portone.sdk.server.webhook.WebhookTransaction;

public interface PaymentPort {
    PaidPayment getPayment(String paymentId);
    WebhookTransaction verifyWebhook(String body, String id, String timestamp, String signature);
}
