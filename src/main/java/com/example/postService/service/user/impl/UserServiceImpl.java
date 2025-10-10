package com.example.postService.service.user.impl;

import com.example.postService.dto.login.request.LoginRequestDto;
import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.request.UpdateUserPasswordRequestDto;
import com.example.postService.dto.user.request.UpdateUserProfileRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.dto.user.session.SessionUser;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import com.example.postService.mapper.user.UserMapper;
import com.example.postService.repository.user.UserJpaRepository;
import com.example.postService.repository.user.UserProfileJpaRepository;
import com.example.postService.service.user.UserService;
import com.example.postService.util.PasswordEncoderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor//private final 로 선언된 객체들의 생성자들을 자동으로 생성해줌
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    /*
        회원가입
     */
    @Override
    @Transactional
    public CreateUserResponseDto signUp(CreateUserRequestDto dto) {
        // 이메일 중복 체크
        Optional<User> userOptional = userJpaRepository.findByEmail(dto.getEmail());

        //이메일 중복 시 예외 처리
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        //BCrypt 방식으로 패스워드 암호화 진행
        String encodedPassword = PasswordEncoderUtil.encode(dto.getPassword());

        //userProfile 엔터티 생성
        UserProfile userProfile = userMapper.createUserRequestDtoToUserProfile(dto);

        //패스워드 암호화된 패스워드로 업데이트(mapper에서 실제 데이터를 사용하지 않게 하기 위해서)
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();

        //user 엔터티 생성
        User user = userMapper.createUserRequestDto(createUserRequestDto, userProfile);

        //DB 저장
        userJpaRepository.save(user);

        return userMapper.userToCreateUserResponseDto(user);

    }


    //로그인 처리 Service 로직
    @Override
    public ResponseEntity<String> login(LoginRequestDto dto, HttpServletRequest request) {

        //입력받은 email을 DB에서 조회
        Optional<User> userOptional = userJpaRepository.findByEmail(dto.getEmail());

        //해당하는 email의 user가 없을 시 예외 처리
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 이메일의 유저가 없습니다.");
        }//passwordEncoderUtil에서 생성해놓은 matches메서드를 통해 입력 password와 암호화된 password를 비교

        User user = userOptional.get();
        if (!PasswordEncoderUtil.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("비밀번호가 틀렸습니다.");
        }

        UserProfile userProfile = user.getUserProfile();

        //세션에 저장할 정보 dto 변환
        SessionUser sessionUser = userMapper.userProfileToSessionUser(userProfile);

        //세션 생성
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", sessionUser);
        httpSession.setMaxInactiveInterval(60 * 30);//세션 만료 시간 설정(30분)


        return ResponseEntity.ok("로그인 성공!");

    }

    //회원 정보 조회 Service 로직
    @Override
    public ResponseEntity<GetUserResponseDto> get(Long userId) {
        Optional<User> userOptional = userJpaRepository.findById(userId);//PathVariable로 부터 온 userId를 통해 DB에서 User조회
        //Optional 안할거면 왜 Optional 반환?

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }//해당 user없을시 예외 처리

        User user = userOptional.get();
        //Mapper을 통해 응답 dto 변환 후 반환
        return ResponseEntity.ok(userMapper.userToUGetUserResponseDto(user));
    }

    //닉네임,프로필 이미지 수정 Service 로직
    @Override
    @Transactional
    public ResponseEntity<String> updateProfile(UpdateUserProfileRequestDto dto, Long userId) {

//        PathVariable로 부터 온 userId를 통해 DB에서 UserProfile조회
        Optional<UserProfile> userProfileOptional = userProfileJpaRepository.findById(userId);

        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }//해당 userProfile없을 시 예외 처리

//      UserProfile클래스 업데이트 메서드를 통해서 profile 업데이트
        userProfileOptional.get().updateProfile(dto.getNickname(), dto.getProfileImage());

        return ResponseEntity.ok("닉네임,프로필 이미지 수정 성공!");
    }

    //비밀번호 수정 Service 로직
    @Override
    @Transactional
    public ResponseEntity<String> updatePassword(UpdateUserPasswordRequestDto dto, Long userId) {

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body("비밀번호와 비밀번호 확인히 일치하지 않습니다.");
        }//입력된 비밀번호/비밀번호 확인이 일치한지 확인 예외 처리

        //PathVariable로 부터 온 userId를 통해 DB에서 User조회
        Optional<User> userOptional = userJpaRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }//해당하는 User객체 없을 시 예외 처리

        //BCrypt 방식으로 패스워드 암호화 진행
        String encodedPassword = PasswordEncoderUtil.encode(dto.getNewPassword());

        //User클래스 업데이트 메서드를 통해 password 업데이트
        userOptional.get().updatePassword(encodedPassword);

        return ResponseEntity.ok("비밀번호 변경 성공");


    }

    //soft_delete Service 로직
    @Override
    @Transactional
    public ResponseEntity<String> softDelete(Long userId) {

        //PathVariable로 부터 온 userId를 통해 DB에서 User조회
        Optional<User> userOptional = userJpaRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }//해당하는 User객체 없을 시 예외 처리

        //isDeleted true(삭제됨)으로 업데이트, deleted_at 업데이트
        userOptional.get().updateDeleted();

        //사용자에게는 회원탈퇴 성공 메세지 반환
        return ResponseEntity.ok("회원탈퇴 성공");

    }
}
