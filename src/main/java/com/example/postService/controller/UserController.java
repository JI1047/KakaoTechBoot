package com.example.postService.controller;

import com.example.postService.dto.user.request.UpdateUserPasswordRequestDto;
import com.example.postService.service.user.UserService;
import com.example.postService.dto.login.request.LoginRequestDto;
import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.request.UpdateUserProfileRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> signUpUser(@RequestBody CreateUserRequestDto dto) {
        CreateUserResponseDto createUserResponseDto = userService.signUp(dto);
        return ResponseEntity.ok(createUserResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto dto) {

        return userService.UserLogin(dto);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponseDto> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);

    }

    @PatchMapping("/{userId}/profile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateUserProfileRequestDto dto, @PathVariable Long userId) {

        return userService.updateProfile(dto, userId);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdateUserPasswordRequestDto dto, @PathVariable Long userId) {
        return userService.updatePassword(dto, userId);
    }

    @PatchMapping("/{userId}/delete")
    public ResponseEntity<String> softDelete(@PathVariable Long userId) {
        return userService.softDelete(userId);
    }
}
