package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    @DisplayName("UserEntity -> User 도메인 변환 테스트")
    void toDomainTest() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id("user-123")
                .name("테스트 사용자")
                .email("test@example.com")
                .build();

        // when
        User user = UserMapper.toDomain(userEntity);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(userEntity.getId());
        assertThat(user.getName()).isEqualTo(userEntity.getName());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
    }

    @Test
    @DisplayName("User 도메인 -> UserEntity 변환 테스트")
    void toEntityTest() {
        // given
        User user = User.builder()
                .userId("user-456")
                .name("새 사용자")
                .email("new@example.com")
                .build();

        // when
        UserEntity userEntity = UserMapper.toEntity(user);

        // then
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isEqualTo(user.getUserId());
        assertThat(userEntity.getName()).isEqualTo(user.getName());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("null 사용자 변환 테스트")
    void nullUserTest() {
        // then
        assertThat(UserMapper.toEntity(null)).isNull();
        assertThat(UserMapper.toDomain(null)).isNull();
    }
}