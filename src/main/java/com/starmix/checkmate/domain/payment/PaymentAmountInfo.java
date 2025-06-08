package com.starmix.checkmate.domain.payment;

import io.portone.sdk.server.payment.PaymentAmount;
import lombok.Builder;

@Builder
public record PaymentAmountInfo(
        String total,
        String taxFree,
        String vat,
        String supply,
        String discount,
        String paid,
        String cancelled,
        String cancelledTaxFree
) {
    public static PaymentAmountInfo init(PaymentAmount amount) {
        return PaymentAmountInfo.builder()
                .total(String.valueOf(amount.getTotal()))
                .taxFree(String.valueOf(amount.getTaxFree()))
                .vat(String.valueOf(amount.getVat()))
                .supply(String.valueOf(amount.getSupply()))
                .discount(String.valueOf(amount.getDiscount()))
                .paid(String.valueOf(amount.getPaid()))
                .cancelled(String.valueOf(amount.getCancelled()))
                .cancelledTaxFree(String.valueOf(amount.getCancelledTaxFree()))
                .build();
    }
}