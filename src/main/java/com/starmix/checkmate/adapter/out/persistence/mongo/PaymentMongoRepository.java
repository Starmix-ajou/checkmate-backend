package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMongoRepository extends MongoRepository<PaymentEntity, String> {
    List<PaymentEntity> findAllByOrderName(String orderName);
}
