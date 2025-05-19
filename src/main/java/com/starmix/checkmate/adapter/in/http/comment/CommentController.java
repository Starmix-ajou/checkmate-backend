package com.starmix.checkmate.adapter.in.http.comment;

import com.starmix.checkmate.adapter.in.http.comment.request.CommentRequest;
import com.starmix.checkmate.adapter.in.http.comment.response.CommentResponse;
import com.starmix.checkmate.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByTaskId(
            @RequestParam String taskId
    ) {
        List<CommentResponse> response = commentService.getCommentsByTaskId(taskId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestParam String taskId,
            @RequestBody CommentRequest request
    ) {
        commentService.createComment(taskId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable String commentId,
            @RequestBody CommentRequest request
    ) {
        commentService.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable String commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}