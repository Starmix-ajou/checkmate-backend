package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.user.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String userId);
    void save(User user);
}
