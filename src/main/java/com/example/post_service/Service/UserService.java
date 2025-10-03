package com.example.post_service.Service;

import com.example.post_service.Dto.SignupRequestDto;
import com.example.post_service.Dto.SignupResponseDto;
import com.example.post_service.Entity.user.UserBasic;
import com.example.post_service.Entity.user.UserExtra;
import com.example.post_service.Mapper.UserMapper;
import com.example.post_service.Repository.UserBasicRepository;
import com.example.post_service.Repository.UserExtraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserBasicRepository userBasicRepository;
    private final UserExtraRepository userExtraRepository;

    public UserService(UserBasicRepository userBasicRepository, UserExtraRepository userExtraRepository) {
        this.userBasicRepository = userBasicRepository;
        this.userExtraRepository = userExtraRepository;
    }

    @Autowired
    private UserMapper userMapper;

    //회원가입을 처리하는 service
    public SignupResponseDto UserSignUp(SignupRequestDto dto) {
        // 이메일 중복 체크
        if (userBasicRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (userExtraRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // 비밀번호 암호화
//        String encodedPassword = passwordEncoder.encode(dto.getPassword());


        UserExtra userExtra = userMapper.toUserExtra(dto);
        UserBasic userBasic = userMapper.toUserBasic(dto, userExtra);

        userBasicRepository.save(userBasic);

        SignupResponseDto responseDto = userMapper.toSignupResponseDto(userBasic);
        return responseDto;

    }
}
