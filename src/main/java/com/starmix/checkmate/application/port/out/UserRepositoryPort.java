package com.starmix.checkmate.application.port.out;

import com.starmix.checkmate.domain.user.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    User save(User user);
}
