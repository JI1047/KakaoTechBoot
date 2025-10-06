package com.example.postService.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCommentResponseDto {

    private String nickname; //사용자 닉네임

    private String profileImage;//사용자 프로필 이미지


    private String text;

    private LocalDateTime createdAt;


}
