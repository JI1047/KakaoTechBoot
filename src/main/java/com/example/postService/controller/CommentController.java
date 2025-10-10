package com.example.postService.controller;

import com.example.postService.dto.comment.request.CreateCommentDto;
import com.example.postService.dto.comment.request.UpdateCommentDto;
import com.example.postService.service.comment.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CreateCommentDto dto, HttpServletRequest httpServletRequest) {
        return commentService.createComment(postId, dto, httpServletRequest);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody UpdateCommentDto dto, HttpServletRequest httpServletRequest) {
        return commentService.updateComment(postId,commentId,dto,httpServletRequest);

    }


}
