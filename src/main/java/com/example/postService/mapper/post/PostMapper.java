package com.example.postService.mapper.post;

import com.example.postService.dto.post.response.GetListPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostContent;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

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

    public GetListPostResponseDto toGetListPostResponseDto(Post post,PostView postView, UserProfile userProfile) {
        return GetListPostResponseDto.builder()
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .likeCount(postView.getLikeCount())
                .commentCount(postView.getCommentCount())
                .lookCount(postView.getLookCount())
                .nickname(userProfile.getNickname())
                .profileImage(userProfile.getProfileImage())
                .build();
    }

}
