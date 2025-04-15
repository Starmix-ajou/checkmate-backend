package com.starmix.checkmate.adapter.in.comment;

import com.starmix.checkmate.application.service.CommentService;
import com.starmix.checkmate.domain.comment.Comment;
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
    public ResponseEntity<List<Comment>> getCommentsByTaskId (
            @RequestParam(required = false) String taskId
    ) {
        List<Comment> tasks = commentService.getCommentsByTaskId(taskId);
        return ResponseEntity.ok().body(tasks);
    }
}