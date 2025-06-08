package com.starmix.checkmate.domain.payment;

import io.portone.sdk.server.payment.PaidPayment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
public class Payment {
    private String paymentId;
    private PaymentStatus status;
    private String orderName;
    private String totalAmount;
    private String currency;
    private String payMethod;
    private LocalDateTime timestamp;

    public static Payment init(
            String paymentId,
            PaymentStatus status,
            PaidPayment paidPayment
    ) {
        return Payment.builder()
                .paymentId(paymentId)
                .status(status)
                .orderName(paidPayment.getOrderName())
                .totalAmount(paidPayment.getAmount().toString())
                .currency(paidPayment.getCurrency().toString())
                .payMethod(Objects.requireNonNull(paidPayment.getMethod()).toString())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
