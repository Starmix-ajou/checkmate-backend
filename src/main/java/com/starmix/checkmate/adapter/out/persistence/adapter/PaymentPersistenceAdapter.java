package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.*;
import com.starmix.checkmate.adapter.out.persistence.mapper.PaymentMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.PaymentMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.PaymentPersistencePort;
import com.starmix.checkmate.domain.payment.Payment;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PaymentPersistenceAdapter implements PaymentPersistencePort {
    private final PaymentMongoRepository paymentMongoRepository;

    @Override
    public Optional<Payment> findById(String id) {
        try {
            Optional<PaymentEntity> optionalPaymentEntity =  paymentMongoRepository.findById(id);
            return optionalPaymentEntity.map(PaymentMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(Payment payment) {
        try {
            PaymentEntity entity = PaymentMapper.toEntity(payment);
            paymentMongoRepository.save(entity);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Payment> findAllByProjectId(String projectId) {
        try {
            List<PaymentEntity> payments = paymentMongoRepository.findAllByProjectId(projectId);
            return payments.stream().map(PaymentMapper::toDomain).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
