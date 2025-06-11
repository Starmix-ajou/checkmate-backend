package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.PaymentEntity;
import com.starmix.checkmate.domain.payment.Payment;
import com.starmix.checkmate.domain.payment.PaymentAmountInfo;
import com.starmix.checkmate.domain.payment.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentMapperTest {

    @Test
    @DisplayName("PaymentEntity -> Payment 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDateTime now = LocalDateTime.now();
        PaymentAmountInfo amountInfo = PaymentAmountInfo.builder()
                .total(String.valueOf(10000))
                .build();

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .id("payment-123")
                .status(PaymentStatus.PAID)
                .orderName("테스트 주문")
                .totalAmount(amountInfo)
                .currency("KRW")
                .payMethod("card")
                .timestamp(now)
                .projectId("project-123")
                .build();

        // when
        Payment payment = PaymentMapper.toDomain(paymentEntity);

        // then
        assertThat(payment).isNotNull();
        assertThat(payment.getPaymentId()).isEqualTo(paymentEntity.getId());
        assertThat(payment.getStatus()).isEqualTo(paymentEntity.getStatus());
        assertThat(payment.getOrderName()).isEqualTo(paymentEntity.getOrderName());
        assertThat(payment.getTotalAmount()).isEqualTo(paymentEntity.getTotalAmount());
        assertThat(payment.getCurrency()).isEqualTo(paymentEntity.getCurrency());
        assertThat(payment.getPayMethod()).isEqualTo(paymentEntity.getPayMethod());
        assertThat(payment.getTimestamp()).isEqualTo(paymentEntity.getTimestamp());
        assertThat(payment.getProjectId()).isEqualTo(paymentEntity.getProjectId());
    }

    @Test
    @DisplayName("Payment 도메인 -> PaymentEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDateTime now = LocalDateTime.now();
        PaymentAmountInfo amountInfo = PaymentAmountInfo.builder()
                .total(String.valueOf(20000))
                .build();

        Payment payment = Payment.builder()
                .paymentId("payment-456")
                .status(PaymentStatus.PENDING)
                .orderName("새 주문")
                .totalAmount(amountInfo)
                .currency("USD")
                .payMethod("bank_transfer")
                .timestamp(now)
                .projectId("project-456")
                .build();

        // when
        PaymentEntity paymentEntity = PaymentMapper.toEntity(payment);

        // then
        assertThat(paymentEntity).isNotNull();
        assertThat(paymentEntity.getId()).isEqualTo(payment.getPaymentId());
        assertThat(paymentEntity.getStatus()).isEqualTo(payment.getStatus());
        assertThat(paymentEntity.getOrderName()).isEqualTo(payment.getOrderName());
        assertThat(paymentEntity.getTotalAmount()).isEqualTo(payment.getTotalAmount());
        assertThat(paymentEntity.getCurrency()).isEqualTo(payment.getCurrency());
        assertThat(paymentEntity.getPayMethod()).isEqualTo(payment.getPayMethod());
        assertThat(paymentEntity.getTimestamp()).isEqualTo(payment.getTimestamp());
        assertThat(paymentEntity.getProjectId()).isEqualTo(payment.getProjectId());
    }

    @Test
    @DisplayName("다양한 결제 상태 변환 테스트")
    void paymentStatusTest() {
        // given
        PaymentEntity paidEntity = PaymentEntity.builder()
                .id("payment-paid")
                .status(PaymentStatus.PAID)
                .build();

        PaymentEntity cancelledEntity = PaymentEntity.builder()
                .id("payment-cancelled")
                .status(PaymentStatus.FAILED)
                .build();

        PaymentEntity failedEntity = PaymentEntity.builder()
                .id("payment-failed")
                .status(PaymentStatus.FAILED)
                .build();

        // when
        Payment paidPayment = PaymentMapper.toDomain(paidEntity);
        Payment cancelledPayment = PaymentMapper.toDomain(cancelledEntity);
        Payment failedPayment = PaymentMapper.toDomain(failedEntity);

        // then
        assertThat(paidPayment.getStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(cancelledPayment.getStatus()).isEqualTo(PaymentStatus.FAILED);
        assertThat(failedPayment.getStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    @DisplayName("결제 금액 정보 변환 테스트")
    void amountInfoTest() {
        // given
        PaymentAmountInfo amountInfo = PaymentAmountInfo.builder()
                .total(String.valueOf(15000))
                .taxFree(String.valueOf(500))
                .build();

        Payment paymentWithAmount = Payment.builder()
                .paymentId("payment-amount")
                .totalAmount(amountInfo)
                .build();

        // when
        PaymentEntity entity = PaymentMapper.toEntity(paymentWithAmount);

        // then
        assertThat(entity.getTotalAmount()).isNotNull();
        assertThat(entity.getTotalAmount().total()).isEqualTo(String.valueOf(15000));
        assertThat(entity.getTotalAmount().taxFree()).isEqualTo(String.valueOf(500));
    }

    @Test
    @DisplayName("최소 필드만 있는 결제 변환 테스트")
    void minimalPaymentTest() {
        // given
        PaymentEntity minimalEntity = PaymentEntity.builder()
                .id("payment-minimal")
                .status(PaymentStatus.PENDING)
                .build();

        Payment minimalPayment = Payment.builder()
                .paymentId("payment-minimal-domain")
                .status(PaymentStatus.PENDING)
                .build();

        // when
        Payment resultDomain = PaymentMapper.toDomain(minimalEntity);
        PaymentEntity resultEntity = PaymentMapper.toEntity(minimalPayment);

        // then
        assertThat(resultDomain).isNotNull();
        assertThat(resultDomain.getPaymentId()).isEqualTo(minimalEntity.getId());
        assertThat(resultDomain.getStatus()).isEqualTo(minimalEntity.getStatus());

        assertThat(resultEntity).isNotNull();
        assertThat(resultEntity.getId()).isEqualTo(minimalPayment.getPaymentId());
        assertThat(resultEntity.getStatus()).isEqualTo(minimalPayment.getStatus());
    }

    @Test
    @DisplayName("null 결제 변환 테스트")
    void nullPaymentTest() {
        // given & when & then
        assertThatThrownBy(() -> PaymentMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> PaymentMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}