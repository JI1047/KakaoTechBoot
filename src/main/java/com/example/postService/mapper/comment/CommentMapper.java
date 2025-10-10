package com.example.postService.mapper.comment;

import com.example.postService.dto.comment.request.CreateCommentDto;
import com.example.postService.dto.comment.response.GetCommentResponseDto;
import com.example.postService.entity.comment.Comment;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;

@Component
public class CommentMapper {

    public GetCommentResponseDto getCommentResponseDto(Comment comment, UserProfile userProfile){

        return GetCommentResponseDto.builder()
                        .nickname(userProfile.getNickname())
                        .profileImage(userProfile.getProfileImage())
                        .text(comment.getText())
                        .createdAt(comment.getCreatedAt())
                        .build();
    }

    public Comment toComment(CreateCommentDto dto, Post post, UserProfile userProfile) {
        return Comment.builder()
                .text(dto.getText())
                .userProfile(userProfile)
                .post(post)
                .build();
    }

}
