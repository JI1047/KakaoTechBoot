package com.example.postService.service.Post.impl;

import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.post.PostMapper;
import com.example.postService.mapper.user.UserMapper;
import com.example.postService.repository.post.PostJpaRepository;
import com.example.postService.repository.post.PostViewJpaRepository;
import com.example.postService.repository.user.UserJpaRepository;
import com.example.postService.repository.user.UserProfileJpaRepository;
import com.example.postService.service.Post.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostJpaRepository postJpaRepository;
    private final PostViewJpaRepository postViewJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Transactional
    @Override
    public ResponseEntity<String> CreatePost(CreatePostRequestDto dto) {

        UserProfile userProfile = userProfileJpaRepository.findById(dto.getUserId())
                .orElse(null);

        if (userProfile == null) {
            return ResponseEntity.notFound().build();
        }

        PostView postView = new PostView(0, 0, 0);

        Post post = postMapper.toPost(dto,postView,userProfile);

        postJpaRepository.save(post);


        return ResponseEntity.ok("게시글 생성 성공!");

    }


}
