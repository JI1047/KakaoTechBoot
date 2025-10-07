package com.example.postService.mapper.user;

import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import org.springframework.stereotype.Component;

@Component//컴포넌트 등록 후 Service 아키텍처에서 의존성 주입으로 활용하기 위해 등록
public class UserMapper {

    //회원가입시 받은 CreateUserRequestDto을 UserProfile로 변환
    public UserProfile toUserProfile(CreateUserRequestDto dto) {
        return UserProfile.builder()
                .nickname(dto.getNickname())
                .profileImage(dto.getProfileImage())
                .build();
    }

    //reateUserRequestDto와 UserProfile을 통해 User로 변환
    public User toUser(CreateUserRequestDto dto, UserProfile userProfile, String encodedPassword) {
        return User.builder()
                .userProfile(userProfile)
                .email(dto.getEmail())
                .password(encodedPassword)
                .isDeleted(false)
                .build();
    }

    //회원가입 완료 후 클라이언트에 반환할 응답 dto로 변환
    public CreateUserResponseDto toSignupResponseDto(User user) {
        return CreateUserResponseDto.builder()
                .nickname(user.getUserProfile().getNickname())
                .profileImage(user.getUserProfile().getProfileImage())
                .email(user.getEmail())
                .build();
    }

    //회원정보 조회 이후 클라이언트에 반환할 응답 dto로 변환
    public GetUserResponseDto toUserResponseDto(User user) {
        return GetUserResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getUserProfile().getNickname())
                .profileImage(user.getUserProfile().getProfileImage())
                .build();
    }
}
