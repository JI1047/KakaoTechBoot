package com.example.postService.service.comment.impl;

import com.example.postService.dto.comment.request.CreateCommentDto;
import com.example.postService.dto.comment.request.UpdateCommentDto;
import com.example.postService.dto.user.session.SessionUser;
import com.example.postService.entity.comment.Comment;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.comment.CommentMapper;
import com.example.postService.repository.comment.CommentJpaRepository;
import com.example.postService.repository.post.PostJpaRepository;
import com.example.postService.repository.user.UserProfileJpaRepository;
import com.example.postService.service.comment.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final CommentJpaRepository commentJpaRepository;

    private final PostJpaRepository postJpaRepository;

    private final UserProfileJpaRepository userProfileJpaRepository;


    //댓글 생성 로직
    @Transactional
    @Override
    public ResponseEntity<String> createComment(Long postId, CreateCommentDto dto, HttpServletRequest httpServletRequest) {

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
        Optional<Post> postOptional = postJpaRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 게시물을 찾을 수 없습니다.");
        }

        UserProfile userProfile = userProfileOptional.get();
        Post post = postOptional.get();

        post.getPostView().commentCountIncrease();

        Comment comment = commentMapper.toComment(dto, post, userProfile);

        commentJpaRepository.save(comment);
        return ResponseEntity.ok("댓글 생성 성공");

    }

    //댓글 수정 로직
    @Transactional
    @Override
    public ResponseEntity<String> updateComment(Long postId, Long commentId, UpdateCommentDto dto, HttpServletRequest httpServletRequest) {

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
        Optional<Post> postOptional = postJpaRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 게시물을 찾을 수 없습니다.");
        }
        Optional<Comment> commentOptional = commentJpaRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 댓글을 찾을 수 없습니다.");
        }
        UserProfile userProfile = userProfileOptional.get();
        Comment comment = commentOptional.get();

        if(!comment.getUserProfile().getId().equals(userProfile.getId())) {
            return ResponseEntity.badRequest().body("작성하신 사용자와 일치하지 않는 사용자입니다.");
        }
        comment.updateText(dto.getText());

        return ResponseEntity.ok("댓글 수정 성공!");
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteComment(Long postId, Long commentId, HttpServletRequest httpServletRequest) {
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
        Optional<Post> postOptional = postJpaRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 게시물을 찾을 수 없습니다.");
        }
        Post post = postOptional.get();
        Optional<Comment> commentOptional = commentJpaRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 댓글을 찾을 수 없습니다.");
        }
        UserProfile userProfile = userProfileOptional.get();
        Comment comment = commentOptional.get();

        if(!comment.getUserProfile().getId().equals(userProfile.getId())) {
            return ResponseEntity.badRequest().body("작성하신 사용자와 일치하지 않는 사용자입니다.");
        }

        post.getPostView().commentCountDecrease();
        commentJpaRepository.delete(comment);

        return ResponseEntity.ok("댓글 삭제 성공");
    }
}
