package com.example.postService.mapper.user;

import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserProfile toUserProfile(CreateUserRequestDto dto) {
        return UserProfile.builder()
                .nickname(dto.getNickname())
                .profileImage(dto.getProfileImage())
                .build();
    }

    public User toUser(CreateUserRequestDto dto, UserProfile UserProfile) {
        return User.builder()
                .userProfile(UserProfile)
                .email(dto.getEmail())
                .password(dto.getPassword())
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
                .build();
    }
}
