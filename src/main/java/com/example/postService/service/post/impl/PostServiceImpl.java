package com.example.postService.service.post.impl;

import com.example.postService.dto.comment.response.GetCommentResponseDto;
import com.example.postService.dto.post.response.GetPostListResponseDto;
import com.example.postService.dto.post.response.GetPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import com.example.postService.dto.user.session.UserSession;
import com.example.postService.entity.comment.Comment;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostContent;
import com.example.postService.entity.post.PostLike;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.comment.CommentMapper;
import com.example.postService.mapper.post.PostMapper;
import com.example.postService.repository.post.PostJpaRepository;
import com.example.postService.repository.post.PostLikeJpaRepository;
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
    private final PostLikeJpaRepository postLikeJpaRepository;


    //게시물 생성 로직
    @Override
    @Transactional
    public ResponseEntity<String> createPost(CreatePostRequestDto createPostRequestDto, HttpServletRequest httpServletRequest) {

        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        UserSession userSession = (UserSession) httpSession.getAttribute("user");
        if (userSession == null || userSession.getUserProfileId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 정보가 유효하지 않습니다.");
        }

        Optional<UserProfile> userProfileOptional = userProfileJpaRepository.findById(userSession.getUserProfileId());
        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 사용자 정보를 찾을 수 없습니다.");
        }

        UserProfile userProfile = userProfileOptional.get();

        //PostView 객체 생성
        PostView postView = PostView.builder().build();

        //PostContent객체 생성
        PostContent postContent = postMapper.postContentDtoToPostContent(createPostRequestDto);

        //Mapper을 통해 게시물 객체 생성
        Post post = postMapper.toPost(createPostRequestDto, postView, userProfile, postContent);


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
    public ResponseEntity<String> deletePost(Long postId) {
        Optional<Post> postOptional = postJpaRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = postOptional.get();

        postJpaRepository.delete(post);

        return ResponseEntity.ok("게시물 삭제 성공");
    }

    /**게시물 목록 조회 로직
     * 게시물 리스트를 page(시작점, 불러올 게시물 size)를 설정해놓고
     *  jpa를 통해 findAll메서드를 호출해서 List 형태로 조회했습니다.
     *  이후 조회,댓글,좋아요 수를 불러오기 위해 lazy로 설정된 postView,UserProfile도를
     *  for문을 통해 불러온 list의 크기만큼 반복하여 조회했습니다.
     *  그러자 List posts를 조회할 때 한번 그리고 List의 크기만큼 postView,UserProfile도를 조회하는
     *  N+1문제를 마주쳣습니다.
     *  queryDSL을 직접 작성하여 fetch join을 통해서 관련된 poseView의 엔터티들을
     *  한번에 조회하여 쿼리문을 줄이는 설계를 통해 최적화를 진행했습니다.
     *
     *  1. 요청을 통해 포함된 page,size를 통해서 pageRequest 설정
     *  2. pageRequest를 포함해 만든 queryDSL로 해당하는 List의 크기만큼 post조회
     *  2-1. 관련 매핑 객체 postView,UserProfile도 한번에 조회
     *  3. 게시물 목록 조회 응답 dto List 생성
     *  3. for문을 통해 미리 불러온 posts에서 mapper를 통해 dto로 변경
     *  4. 응답 dto List 반환
     *
     */
    @Override//게시물 목록 조회
    public ResponseEntity<List<GetPostListResponseDto>> getPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Post> posts = postJpaRepository.findListPostQueryDSL(pageRequest);

        List<GetPostListResponseDto> responseDtoList = new ArrayList<>();
        for (Post post : posts) {
            PostView postView = post.getPostView();
            UserProfile userProfile = post.getUserProfile();

            GetPostListResponseDto getListPostResponseDto =
                    postMapper.toGetPostListResponseDto(post, postView, userProfile);
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
        List<Comment> comments = post.getComments();
        List<GetCommentResponseDto> responseDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponseDto dto = commentMapper.getCommentResponseDto(comment, userProfile);
            responseDtoList.add(dto);
        }

        GetPostResponseDto getPostResponseDto = postMapper.toGetPostResponseDto(post, postContent, postView, userProfile, responseDtoList);
        return ResponseEntity.ok(getPostResponseDto);

    }

    @Transactional
    @Override
    public ResponseEntity<String> updatePostLike(Long postId, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        UserSession userSession = (UserSession) httpSession.getAttribute("user");
        if (userSession == null || userSession.getUserProfileId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 정보가 유효하지 않습니다.");
        }

        Optional<UserProfile> userProfileOptional = userProfileJpaRepository.findById(userSession.getUserProfileId());
        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 사용자 정보를 찾을 수 없습니다.");
        }
        Optional<Post> postOptional = postJpaRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 게시물을 찾을 수 없습니다.");
        }
        Post post = postOptional.get();
        UserProfile userProfile = userProfileOptional.get();

        boolean alreadyLiked = postLikeJpaRepository.existsByPostAndUserProfile(post, userProfile);

        if (alreadyLiked) {
            // 좋아요 취소
            postLikeJpaRepository.deleteByPostAndUserProfile(post, userProfile);
            post.getPostView().likeCountDecrease();
            return ResponseEntity.ok("좋아요 제거");
        } else {
            // 좋아요 추가
            PostLike postLikeEntity = postMapper.toPostLike(post, userProfile);
            postLikeJpaRepository.save(postLikeEntity);
            post.getPostView().likeCountIncrease();
            return ResponseEntity.ok("좋아요 생성");
        }
    }
}
