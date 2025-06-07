package com.starmix.checkmate.adapter.out.payment.adapter;

import com.starmix.checkmate.application.port.out.payment.PaymentPort;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.config.PortOneConfig;
import io.portone.sdk.server.payment.PaidPayment;
import io.portone.sdk.server.payment.Payment;
import io.portone.sdk.server.webhook.WebhookTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentPort {

    private final PortOneConfig portOneConfig;

    @Override
    public PaidPayment getPayment(String paymentId) {
        try {
            Payment payment = portOneConfig.paymentClient().getPayment(paymentId).get();
            if (payment instanceof PaidPayment paidPayment) {
                return paidPayment;
            } else {
                throw new CustomException("결제 상태가 PAID가 아님", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new CustomException("결제 조회 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WebhookTransaction verifyWebhook(String body, String id, String timestamp, String signature) {
        try {
            return (WebhookTransaction) portOneConfig.webhookVerifier().verify(body, id, timestamp, signature);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}