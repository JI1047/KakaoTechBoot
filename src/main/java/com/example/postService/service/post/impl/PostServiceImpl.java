package com.example.postService.service.post.impl;

import com.example.postService.dto.comment.response.GetCommentResponseDto;
import com.example.postService.dto.post.response.GetPostListResponseDto;
import com.example.postService.dto.post.response.GetPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import com.example.postService.dto.user.session.SessionUser;
import com.example.postService.entity.comment.Comment;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostContent;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.comment.CommentMapper;
import com.example.postService.mapper.post.PostMapper;
import com.example.postService.repository.post.PostJpaRepository;
import com.example.postService.repository.post.PostViewJpaRepository;
import com.example.postService.repository.user.UserProfileJpaRepository;
import com.example.postService.service.post.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final PostJpaRepository postJpaRepository;
    private final PostViewJpaRepository postViewJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;


    //게시물 생성 로직
    @Override
    @Transactional
    public ResponseEntity<String> createPost(CreatePostRequestDto createPostRequestDto, HttpServletRequest httpServletRequest) {

        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserProfileId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 정보가 유효하지 않습니다.");
        }

        Optional<UserProfile> userProfileOptional = userProfileJpaRepository.findById(sessionUser.getUserProfileId());
        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 사용자 정보를 찾을 수 없습니다.");
        }

        UserProfile userProfile = userProfileOptional.get();

        //PostView 객체 생성
        PostView postView = PostView.builder().build();

        //PostContent객체 생성
        PostContent postContent = postMapper.postContentDtoToPostContent(createPostRequestDto);

        //Mapper을 통해 게시물 객체 생성
        Post post = postMapper.toPost(createPostRequestDto,postView,userProfile,postContent);


        //게시물 저장
        postJpaRepository.save(post);


        return ResponseEntity.ok("게시글 생성 성공!");

    }

    @Transactional
    @Override
    public ResponseEntity<String> updatePost(UpdatePostRequestDto dto, Long postId) {
        Optional<Post> postOptional = postJpaRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOptional.get();

        PostContent postContent = post.getPostContent();


        postContent.updatePostContent(dto);
        post.updatePost(dto);

        return ResponseEntity.ok("게시물 수정 성공");
    }

    @Transactional
    @Override
    public ResponseEntity<String> DeletePost(Long postId) {
        Optional<Post> postOptional = postJpaRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = postOptional.get();

        postJpaRepository.delete(post);

        return ResponseEntity.ok("게시물 삭제 성공");
    }

    @Override//게시물 목록 조회
    public ResponseEntity<List<GetPostListResponseDto>> getPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Post> posts = postJpaRepository.findListPostQueryDSL(pageRequest);

        List<GetPostListResponseDto> responseDtoList =new ArrayList<>();
        for (Post post : posts) {
            PostView postView = post.getPostView();
            UserProfile userProfile = post.getUserProfile();

            GetPostListResponseDto getListPostResponseDto =
                    postMapper.toGetPostListResponseDto(post,postView,userProfile);
            responseDtoList.add(getListPostResponseDto);

        }

        return ResponseEntity.ok(responseDtoList);

    }

    @Override
    public ResponseEntity<GetPostResponseDto> getPost(Long postId) {
        Post post = postJpaRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        PostContent postContent = post.getPostContent();
        PostView postView = post.getPostView();
        UserProfile userProfile = post.getUserProfile();
        List<Comment> comments =post.getComments();
        List<GetCommentResponseDto> responseDtoList =new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponseDto dto = commentMapper.getCommentResponseDto(comment,userProfile);
            responseDtoList.add(dto);
        }

        GetPostResponseDto getPostResponseDto = postMapper.toGetPostResponseDto(post, postContent, postView, userProfile, responseDtoList);
        return ResponseEntity.ok(getPostResponseDto);

    }



}
