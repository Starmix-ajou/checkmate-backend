package com.starmix.checkmate.domain.payment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment {
    private String paymentId;
    private PaymentStatus status;

    public static Payment init(String paymentId, PaymentStatus status) {
        return Payment.builder()
                .paymentId(paymentId)
                .status(status)
                .build();
    }

    public void changeStatus(PaymentStatus status) {
        this.status = status;
    }
}
