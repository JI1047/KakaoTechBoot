package com.example.post_service.Controller;

import com.example.post_service.Dto.User.LoginRequestDto;
import com.example.post_service.Dto.User.SignupRequestDto;
import com.example.post_service.Dto.User.SignupResponseDto;
import com.example.post_service.Dto.User.UserResponseDto;
import com.example.post_service.Entity.user.UserBasic;
import com.example.post_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUpUser(@RequestBody SignupRequestDto dto) {
        SignupResponseDto signupResponseDto = userService.UserSignUp(dto);
        return ResponseEntity.ok(signupResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto dto) {

        return userService.UserLogin(dto);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);

    }
}
