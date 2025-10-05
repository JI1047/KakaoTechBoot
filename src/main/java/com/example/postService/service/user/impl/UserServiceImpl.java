package com.example.postService.service.user.impl;

import com.example.postService.dto.login.request.LoginRequestDto;
import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.request.UpdateUserPasswordRequestDto;
import com.example.postService.dto.user.request.UpdateUserProfileRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.user.UserMapper;
import com.example.postService.repository.user.UserJpaRepository;
import com.example.postService.repository.user.UserProfileJpaRepository;
import com.example.postService.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    //회원가입을 처리하는 service
    @Transactional
    @Override
    public CreateUserResponseDto signUp(CreateUserRequestDto dto) {
        // 이메일 중복 체크
        Optional<User> userOptional = userJpaRepository.findByEmail(dto.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
//        String encodedPassword = passwordEncoder.encode(dto.getPassword());


        UserProfile UserProfile = userMapper.toUserProfile(dto);
        User User = userMapper.toUser(dto, UserProfile);

        userJpaRepository.save(User);

        return userMapper.toSignupResponseDto(User);

    }


    //로그인 처리 Service 로직
    @Override
    public ResponseEntity<String> UserLogin(LoginRequestDto dto) {

        User User = userJpaRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (User == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 이메일의 유저가 없습니다.");
        }
        if(!User.getPassword().equals(dto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("비밀번호가 틀렸습니다.");
        }

        return ResponseEntity.ok("로그인 성공!");

    }

    //회원 정보 조회 Service 로직
    @Override
    public ResponseEntity<GetUserResponseDto> getUser(Long userId){
        User User = userJpaRepository.findById(userId).orElse(null);

        if (User ==null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toUserResponseDto(User));
    }

    //닉네임,프로필 이미지 수정 Service 로직
    @Override
    @Transactional
    public ResponseEntity<String> updateProfile(UpdateUserProfileRequestDto dto, Long userId) {

        Optional<UserProfile> userProfileOptional = userProfileJpaRepository.findById(userId);

        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userProfileOptional.get().updateProfile(dto.getNickname(), dto.getProfileImage());

        return ResponseEntity.ok("닉네임,프로필 이미지 수정 성공!");
    }

    @Override
    public ResponseEntity<String> updatePassword(UpdateUserPasswordRequestDto dto, Long userId) {

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body("비밀번호와 비밀번호 확인히 일치하지 않습니다.");
        }

        Optional<User> userOptional = userJpaRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userOptional.get().updatePassword(dto.getNewPassword());

        return ResponseEntity.ok("비밀번호 변경 성공");


    }

    @Override
    public ResponseEntity<String> softDelete(Long userId){
        Optional<User> userOptional = userJpaRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userOptional.get().updateDelete(true, LocalDateTime.now());
        return ResponseEntity.ok("회원탈퇴 성공");

    }
}
