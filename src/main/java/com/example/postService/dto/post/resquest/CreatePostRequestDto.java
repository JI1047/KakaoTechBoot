package com.example.postService.dto.post.resquest;

import com.example.postService.entity.user.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequestDto {
    private Long userId;

    private String title;

    private String text;

    private String postImage;
}
