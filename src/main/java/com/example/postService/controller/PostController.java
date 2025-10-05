package com.example.postService.controller;

import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.entity.post.PostView;
import com.example.postService.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequestDto dto) {
        return postService.CreatePost(dto);
    }
}
