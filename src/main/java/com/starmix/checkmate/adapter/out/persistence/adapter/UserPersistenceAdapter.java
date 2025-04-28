package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.UserMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.UserMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserMongoRepository userMongoRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Optional<UserEntity> userEntity = userMongoRepository.findByEmail(email);
            return userEntity.map(UserMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException("UserPersistencePort", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        try {
            Optional<UserEntity> userEntity = userMongoRepository.findById(userId);
            return userEntity.map(UserMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException("UserPersistencePort", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(User user) {
        try {
            UserEntity userEntity = UserMapper.toEntity(user);
            userMongoRepository.save(userEntity);
        } catch (Exception e) {
            throw new CustomException("UserPersistencePort", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
