package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.payment.PaymentPort;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.payment.Payment;
import com.starmix.checkmate.domain.payment.PaymentStatus;
import com.starmix.checkmate.global.exception.CustomException;
import io.portone.sdk.server.payment.PaidPayment;
import io.portone.sdk.server.webhook.WebhookTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentPort paymentPort;
    private final RedisPort redisPort;

    public Payment completePayment(String paymentId) {
        Payment payment = redisPort.getObject(RedisType.PAYMENT_INFO, paymentId);

        PaidPayment actualPayment = paymentPort.getPayment(paymentId);
        if (actualPayment.getCustomData() == null) {
            throw new CustomException("Sync Payment Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (payment != null && payment.getStatus().equals(PaymentStatus.PAID)) {
            return payment;
        } else {
            payment = Payment.init(paymentId, PaymentStatus.PAID, actualPayment);
            redisPort.saveObject(RedisType.PAYMENT_INFO, paymentId, payment, 3, TimeUnit.MINUTES);
            return payment;
        }
    }

    public void handleWebhook(String body, String id, String timestamp, String signature) {
        WebhookTransaction webhook = paymentPort.verifyWebhook(body, id, timestamp, signature);
        completePayment(webhook.getData().getPaymentId());
    }
}
