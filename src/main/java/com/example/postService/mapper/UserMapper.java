package com.example.postService.mapper;

import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.entity.BaseTime;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserProfile toUserExtra(CreateUserRequestDto dto) {
        return UserProfile.builder()
                .nickname(dto.getNickname())
                .profileImage(dto.getProfileImage())
                .build();
    }

    public User toUserBasic(CreateUserRequestDto dto, UserProfile UserProfile) {
        return User.builder()
                .user(UserProfile)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .baseTime(new BaseTime())
                .isDeleted(false)
                .build();
    }

    public CreateUserResponseDto toSignupResponseDto(User User) {
        return CreateUserResponseDto.builder()
                .nickname(User.getUserProfile().getNickname())
                .profileImage(User.getUserProfile().getProfileImage())
                .email(User.getEmail())
                .build();
    }

    public GetUserResponseDto toUserResponseDto(User User) {
        return GetUserResponseDto.builder()
                .email(User.getEmail())
                .nickname(User.getUserProfile().getNickname())
                .profileImage(User.getUserProfile().getProfileImage())
                .createdAt(User.getBaseTime().getCreatedAt())
                .updatedAt(User.getBaseTime().getUpdatedAt())
                .build();
    }
}
