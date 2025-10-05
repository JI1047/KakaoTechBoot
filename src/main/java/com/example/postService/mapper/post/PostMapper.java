package com.example.postService.mapper.post;

import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toPost(CreatePostRequestDto dto, PostView postView, UserProfile userProfile) {
        return Post.builder()
                .post(postView)
                .user(userProfile)
                .title(dto.getTitle())
                .text(dto.getText())
                .postImage(dto.getPostImage())
                .build();
    }
}
