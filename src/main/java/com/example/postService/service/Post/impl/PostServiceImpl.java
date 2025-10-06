package com.example.postService.service.Post.impl;

import com.example.postService.dto.post.response.GetListPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostContent;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.post.PostMapper;
import com.example.postService.repository.post.PostJpaRepository;
import com.example.postService.repository.post.PostViewJpaRepository;
import com.example.postService.repository.user.UserProfileJpaRepository;
import com.example.postService.service.Post.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        PostContent postContent = postMapper.toPostContentDto(dto);
        Post post = postMapper.toPost(dto,postView,userProfile,postContent);

        postJpaRepository.save(post);


        return ResponseEntity.ok("게시글 생성 성공!");

    }

    @Transactional
    @Override
    public ResponseEntity<String> UpdatePost(UpdatePostRequestDto dto, Long postId) {
        Post post = postJpaRepository.findById(postId).orElse(null);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        PostContent postContent = post.getPostContent();


        postContent.updatePostContent(dto);
        post.updatePost(dto);

        return ResponseEntity.ok("게시물 수정 성공");
    }

    @Transactional
    @Override
    public ResponseEntity<String> DeletePost(Long postId) {
        Post post = postJpaRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        postJpaRepository.delete(post);
        return ResponseEntity.ok("게시물 삭제 성공");
    }

    @Override
    public ResponseEntity<List<GetListPostResponseDto>> getPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> posts = postJpaRepository.findAll(pageRequest);

        List<GetListPostResponseDto> responseDtoList =new ArrayList<>();
        for (Post post : posts.getContent()) {
            PostView postView = post.getPost();
            UserProfile userProfile = post.getUser();

            GetListPostResponseDto getListPostResponseDto =
                    postMapper.toGetListPostResponseDto(post,postView,userProfile);
            responseDtoList.add(getListPostResponseDto);

        }

        return ResponseEntity.ok(responseDtoList);

    }



}
