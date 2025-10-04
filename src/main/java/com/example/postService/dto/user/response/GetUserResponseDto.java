package com.example.postService.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponseDto {
    private String email;


    private String nickname; //사용자 닉네임


    private String profileImage;//사용자 프로필 이미지

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
