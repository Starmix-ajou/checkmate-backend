package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.payment.PaymentAmountInfo;
import com.starmix.checkmate.domain.payment.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "payment")
@Getter
@SuperBuilder
@NoArgsConstructor
public class PaymentEntity extends BaseEntity {
    private PaymentStatus status;
    private String orderName;
    private PaymentAmountInfo totalAmount;
    private String currency;
    private String payMethod;
    private LocalDateTime timestamp;
    private String projectId;
}