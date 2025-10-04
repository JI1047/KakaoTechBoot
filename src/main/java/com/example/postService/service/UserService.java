package com.example.postService.service;

import com.example.postService.dto.login.request.LoginRequestDto;
import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.request.UpdateUserPasswordRequestDto;
import com.example.postService.dto.user.request.UpdateUserProfileRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //회원가입을 처리하는 service
    @Transactional
    CreateUserResponseDto signUp(CreateUserRequestDto dto);

    //로그인 처리 Service 로직
    ResponseEntity<String> UserLogin(LoginRequestDto dto);

    //회원 정보 조회 Service 로직
    ResponseEntity<GetUserResponseDto> getUser(Long UserId);

    //닉네임,프로필 이미지 수정 Service 로직
    ResponseEntity<String> updateProfile(UpdateUserProfileRequestDto dto, Long UserId);

    ResponseEntity<String> updatePassword(UpdateUserPasswordRequestDto dto, Long userId);
}
