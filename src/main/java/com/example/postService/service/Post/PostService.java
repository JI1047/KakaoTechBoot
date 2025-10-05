package com.example.postService.service.Post;

import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import com.example.postService.entity.post.PostView;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

public interface PostService {
    @Transactional
    ResponseEntity<String> CreatePost(CreatePostRequestDto dto);

    @Transactional
    ResponseEntity<String> UpdatePost(UpdatePostRequestDto dto, Long postId);

    @Transactional
    ResponseEntity<String> DeletePost(Long postId);
}
