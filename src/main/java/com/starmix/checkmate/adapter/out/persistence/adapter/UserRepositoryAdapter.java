package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.UserMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.UserMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserPersistencePort {

    private final UserMongoRepository userMongoRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = userMongoRepository.findByEmail(email);
        return userEntity.map(UserMapper::toDomain);
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userMongoRepository.save(userEntity);
    }
}
