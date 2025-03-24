package com.starmix.checkmate.adapter.out.persistence;

import com.starmix.checkmate.application.port.out.UserRepositoryPort;
import com.starmix.checkmate.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryAdapter extends MongoRepository<User, String>, UserRepositoryPort {
    Optional<User> findByEmail(String email);
}
