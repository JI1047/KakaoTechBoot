package com.example.postService.mapper.comment;

import com.example.postService.dto.comment.GetCommentResponseDto;
import com.example.postService.entity.comment.Comment;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

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
}
