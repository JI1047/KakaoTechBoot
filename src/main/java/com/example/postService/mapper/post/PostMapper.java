package com.example.postService.mapper.post;

import com.example.postService.dto.comment.GetCommentResponseDto;
import com.example.postService.dto.post.response.GetPostListResponseDto;
import com.example.postService.dto.post.response.GetPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostContent;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {

    public PostContent toPostContentDto(CreatePostRequestDto dto) {
        return PostContent.builder()
                .text(dto.getText())
                .postImage(dto.getPostImage())
                .build();
    }

    public Post toPost(CreatePostRequestDto dto, PostView postView, UserProfile userProfile,PostContent postContent) {
        return Post.builder()
                .post(postView)
                .postContent(postContent)
                .user(userProfile)
                .title(dto.getTitle())
                .build();
    }

    public GetPostListResponseDto toGetPostListResponseDto(Post post, PostView postView, UserProfile userProfile) {
        return GetPostListResponseDto.builder()
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .likeCount(postView.getLikeCount())
                .commentCount(postView.getCommentCount())
                .lookCount(postView.getLookCount())
                .nickname(userProfile.getNickname())
                .profileImage(userProfile.getProfileImage())
                .build();
    }

    public GetPostResponseDto toGetPostResponseDto(Post post,PostContent postContent, PostView postView, UserProfile userProfile, List<GetCommentResponseDto> responseDtoList) {
        return GetPostResponseDto.builder()
                .title(post.getTitle())
                .text(postContent.getText())
                .postImage(postContent.getPostImage())
                .createdAt(post.getCreatedAt())
                .likeCount(postView.getLikeCount())
                .commentCount(postView.getCommentCount())
                .lookCount(postView.getLookCount())
                .nickname(userProfile.getNickname())
                .profileImage(userProfile.getProfileImage())
                .getCommentResponseDto(responseDtoList)
                .build();
    }

}
