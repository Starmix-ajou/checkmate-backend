package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.CommentEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.comment.Comment;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CommentMapperTest {

    @Test
    @DisplayName("CommentEntity -> Comment 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDateTime now = LocalDateTime.now();
        UserEntity authorEntity = mock(UserEntity.class);
        User mockUser = mock(User.class);

        CommentEntity commentEntity = CommentEntity.builder()
                .id("comment-123")
                .taskId("task-123")
                .author(authorEntity)
                .message("테스트 코멘트")
                .timestamp(now)
                .isModified(false)
                .build();

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toDomain(authorEntity)).thenReturn(mockUser);
            Comment comment = CommentMapper.toDomain(commentEntity);

            // then
            assertThat(comment).isNotNull();
            assertThat(comment.getCommentId()).isEqualTo(commentEntity.getId());
            assertThat(comment.getTaskId()).isEqualTo(commentEntity.getTaskId());
            assertThat(comment.getAuthor()).isEqualTo(mockUser);
            assertThat(comment.getMessage()).isEqualTo(commentEntity.getMessage());
            assertThat(comment.getTimestamp()).isEqualTo(commentEntity.getTimestamp());
            assertThat(comment.getIsModified()).isEqualTo(commentEntity.getIsModified());

            userMapperMock.verify(() -> UserMapper.toDomain(authorEntity));
        }
    }

    @Test
    @DisplayName("Comment 도메인 -> CommentEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDateTime now = LocalDateTime.now();
        User author = mock(User.class);
        UserEntity mockUserEntity = mock(UserEntity.class);

        Comment comment = Comment.builder()
                .commentId("comment-456")
                .taskId("task-456")
                .author(author)
                .message("도메인 코멘트")
                .timestamp(now)
                .isModified(true)
                .build();

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toEntity(author)).thenReturn(mockUserEntity);
            CommentEntity commentEntity = CommentMapper.toEntity(comment);

            // then
            assertThat(commentEntity).isNotNull();
            assertThat(commentEntity.getId()).isEqualTo(comment.getCommentId());
            assertThat(commentEntity.getTaskId()).isEqualTo(comment.getTaskId());
            assertThat(commentEntity.getAuthor()).isEqualTo(mockUserEntity);
            assertThat(commentEntity.getMessage()).isEqualTo(comment.getMessage());
            assertThat(commentEntity.getTimestamp()).isEqualTo(comment.getTimestamp());
            assertThat(commentEntity.getIsModified()).isEqualTo(comment.getIsModified());

            userMapperMock.verify(() -> UserMapper.toEntity(author));
        }
    }

    @Test
    @DisplayName("create 메소드로 생성된 Comment 변환 테스트")
    void createMethodTest() {
        // given
        String taskId = "task-create";
        User author = mock(User.class);
        String message = "새 코멘트 메시지";
        
        Comment createdComment = Comment.create(taskId, author, message);
        UserEntity mockUserEntity = mock(UserEntity.class);

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toEntity(author)).thenReturn(mockUserEntity);
            CommentEntity entity = CommentMapper.toEntity(createdComment);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getTaskId()).isEqualTo(taskId);
            assertThat(entity.getAuthor()).isEqualTo(mockUserEntity);
            assertThat(entity.getMessage()).isEqualTo(message);
            assertThat(entity.getTimestamp()).isNotNull();
            assertThat(entity.getIsModified()).isFalse();
        }
    }

    @Test
    @DisplayName("메시지 업데이트 후 Comment 변환 테스트")
    void updateMessageTest() {
        // given
        User author = mock(User.class);
        UserEntity mockUserEntity = mock(UserEntity.class);
        
        Comment originalComment = Comment.builder()
                .commentId("comment-update")
                .taskId("task-update")
                .author(author)
                .message("원래 메시지")
                .timestamp(LocalDateTime.now().minusHours(1))
                .isModified(false)
                .build();
        
        // 메시지 업데이트
        originalComment.updateMessage("수정된 메시지");

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toEntity(author)).thenReturn(mockUserEntity);
            CommentEntity entity = CommentMapper.toEntity(originalComment);

            // then
            assertThat(entity.getMessage()).isEqualTo("수정된 메시지");
            assertThat(entity.getIsModified()).isTrue();
            assertThat(entity.getTimestamp()).isAfter(originalComment.getTimestamp().minusHours(1));
        }
    }
    
    @Test
    @DisplayName("저자 확인 기능 테스트")
    void isAuthorTest() {
        // given
        User author = User.builder().userId("user-123").name("홍길동").build();
        User otherUser = User.builder().userId("user-456").name("김철수").build();
        
        Comment comment = Comment.builder()
                .commentId("comment-author")
                .author(author)
                .build();
        
        // when
        boolean isAuthor = comment.isAuthor(author);
        boolean isNotAuthor = comment.isAuthor(otherUser);
        
        // then
        assertThat(isAuthor).isTrue();
        assertThat(isNotAuthor).isFalse();
    }

    @Test
    @DisplayName("null Comment 변환 테스트")
    void nullCommentTest() {
        // given & when & then
        assertThatThrownBy(() -> CommentMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> CommentMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}