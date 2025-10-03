package com.example.post_service.Dto.User;

import com.example.post_service.Entity.BaseTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponseDto {
    private String email;


    private String nickname; //사용자 닉네임


    private String profileImage;//사용자 프로필 이미지

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
