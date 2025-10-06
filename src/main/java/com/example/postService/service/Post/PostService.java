package com.example.postService.service.Post;

import com.example.postService.dto.post.response.GetPostListResponseDto;
import com.example.postService.dto.post.response.GetPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    @Transactional
    ResponseEntity<String> CreatePost(CreatePostRequestDto dto);

    @Transactional
    ResponseEntity<String> UpdatePost(UpdatePostRequestDto dto, Long postId);

    @Transactional
    ResponseEntity<String> DeletePost(Long postId);

    ResponseEntity<List<GetPostListResponseDto>> getPosts(int page, int size);

    ResponseEntity<GetPostResponseDto> getPost(Long postId);
}
