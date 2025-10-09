package com.example.postService.controller;

import com.example.postService.dto.user.request.UpdateUserPasswordRequestDto;
import com.example.postService.service.user.UserService;
import com.example.postService.dto.login.request.LoginRequestDto;
import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.request.UpdateUserProfileRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<CreateUserResponseDto> signUp(@Valid @RequestBody CreateUserRequestDto dto) {
        return ResponseEntity.ok(userService.signUp(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto, HttpServletRequest httpServletRequest) {

        return userService.login(dto, httpServletRequest);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponseDto> get(@PathVariable Long userId) {
        return userService.get(userId);

    }

    // TODO : http method 는 get post put delete 만 쓴다
    @PatchMapping("/{userId}/profile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateUserProfileRequestDto dto, @PathVariable Long userId) {

        return userService.updateProfile(dto, userId);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdateUserPasswordRequestDto dto, @PathVariable Long userId) {
        return userService.updatePassword(dto, userId);
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId) {
        return userService.softDelete(userId);
    }

    // TODO : log out은 "사용자를 삭제하려는 행위" 가 아니다
    @PutMapping("/log-out")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 서버 세션 무효화
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        //클라이언트에 저장된 JSESSIONID쿠키 삭제
        return ResponseEntity.ok("Logout successful, session & cookie removed");
    }


}
