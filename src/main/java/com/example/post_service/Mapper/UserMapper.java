package com.example.post_service.Mapper;

import com.example.post_service.Dto.SignupRequestDto;
import com.example.post_service.Dto.SignupResponseDto;
import com.example.post_service.Entity.BaseTime;
import com.example.post_service.Entity.user.UserBasic;
import com.example.post_service.Entity.user.UserExtra;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserExtra toUserExtra(SignupRequestDto dto) {
        return UserExtra.builder()
                .nickname(dto.getNickname())
                .profileImage(dto.getProfileImage())
                .build();
    }

    public UserBasic toUserBasic(SignupRequestDto dto, UserExtra userExtra) {
        return UserBasic.builder()
                .user(userExtra)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .baseTime(new BaseTime())
                .isDeleted(false)
                .build();
    }

    public SignupResponseDto toSignupResponseDto(UserBasic userBasic) {
        return SignupResponseDto.builder()
                .nickname(userBasic.getUser().getNickname())
                .profileImage(userBasic.getUser().getProfileImage())
                .email(userBasic.getEmail())
                .build();
    }
}
