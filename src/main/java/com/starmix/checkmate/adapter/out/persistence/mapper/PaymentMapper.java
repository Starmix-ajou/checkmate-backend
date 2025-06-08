package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.PaymentEntity;
import com.starmix.checkmate.domain.payment.Payment;

public class PaymentMapper {

    public static Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .paymentId(entity.getId())
                .status(entity.getStatus())
                .orderName(entity.getOrderName())
                .totalAmount(entity.getTotalAmount())
                .currency(entity.getCurrency())
                .payMethod(entity.getPayMethod())
                .timestamp(entity.getTimestamp())
                .build();
    }

    public static PaymentEntity toEntity(Payment domain) {
        return PaymentEntity.builder()
                .id(domain.getPaymentId())
                .status(domain.getStatus())
                .orderName(domain.getOrderName())
                .totalAmount(domain.getTotalAmount())
                .currency(domain.getCurrency())
                .payMethod(domain.getPayMethod())
                .timestamp(domain.getTimestamp())
                .build();
    }
}
