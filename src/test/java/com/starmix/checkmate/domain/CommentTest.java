package com.starmix.checkmate.domain;

import com.starmix.checkmate.domain.comment.Comment;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {
    private Comment comment;
    private User author;
    private String taskId;

    @BeforeEach
    void setUp() {
        author = User.builder()
                .userId("author-id")
                .email("author@test.com")
                .name("작성자")
                .build();

        taskId = "task-id";

        comment = Comment.builder()
                .commentId("comment-id")
                .message("테스트 댓글입니다.")
                .author(author)
                .taskId(taskId)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    void createComment() {
        // given
        String newMessage = "새로운 댓글";
        String newTaskId = "new-task-id";

        // when
        Comment newComment = Comment.create(newTaskId, author, newMessage);

        // then
        assertThat(newComment.getMessage()).isEqualTo(newMessage);
        assertThat(newComment.getAuthor()).isEqualTo(author);
        assertThat(newComment.getTaskId()).isEqualTo(newTaskId);
        assertThat(newComment.getTimestamp()).isNotNull();
    }

    @Test
    @DisplayName("댓글 내용 수정 테스트")
    void updateContent() {
        // given
        String newContent = "수정된 댓글 내용";

        // when
        comment.updateMessage(newContent);

        // then
        assertThat(comment.getMessage()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("댓글 작성자 확인 테스트")
    void isAuthor() {
        // given
        User otherUser = User.builder()
                .userId("other-id")
                .email("other@test.com")
                .build();

        // when & then
        assertThat(comment.isAuthor(author)).isTrue();
        assertThat(comment.isAuthor(otherUser)).isFalse();
    }

    @Test
    @DisplayName("댓글 수정 여부 확인 테스트")
    void isModified() {
        // when
        comment.updateMessage("수정된 내용");

        // then
        assertThat(comment.getIsModified()).isTrue();
    }

    @Test
    @DisplayName("댓글의 태스크 일치 여부 확인 테스트")
    void isCommentForTask() {
        // given
        Task otherTask = Task.builder()
                .taskId("other-task-id")
                .build();

        // when & then
        assertThat(comment.getTaskId().equals(taskId)).isTrue();
        assertThat(comment.getTaskId().equals(otherTask.getTaskId())).isFalse();
    }
}