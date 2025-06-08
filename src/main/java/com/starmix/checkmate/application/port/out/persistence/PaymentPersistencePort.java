package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.payment.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentPersistencePort {
    Optional<Payment> findById(String id);
    void save(Payment payment);
    List<Payment> findAllByProjectId(String projectId);
}
