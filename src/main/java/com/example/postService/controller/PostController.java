package com.example.postService.controller;

import com.example.postService.dto.post.response.GetPostListResponseDto;
import com.example.postService.dto.post.response.GetPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import com.example.postService.service.post.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public ResponseEntity<List<GetPostListResponseDto>> getAllPosts(@RequestParam int page, @RequestParam int size){
        return postService.getPosts(page, size);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponseDto> getPost(@PathVariable Long postId) {
        return postService.getPost(postId);

    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequestDto dto, HttpServletRequest httpServletRequest) {

        return postService.createPost(dto, httpServletRequest);
    }

    @PatchMapping("/{postId}/update")
    public ResponseEntity<String> updatePost(@RequestBody UpdatePostRequestDto dto, @PathVariable Long postId) {
        return postService.updatePost(dto, postId);
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return postService.DeletePost(postId);
    }
}
