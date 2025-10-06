package com.example.postService.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostListResponseDto {

    private String title;

    private LocalDateTime createdAt;

    private Integer likeCount;

    private Integer commentCount;

    private Integer lookCount;

    private String nickname; //사용자 닉네임


    private String profileImage;//사용자 프로필 이미지



}
