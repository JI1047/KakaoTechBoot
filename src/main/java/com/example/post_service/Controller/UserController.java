package com.example.post_service.Controller;

import com.example.post_service.Dto.SignupRequestDto;
import com.example.post_service.Dto.SignupResponseDto;
import com.example.post_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/members")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUpUser(@RequestBody SignupRequestDto dto) {
        SignupResponseDto signupResponseDto = userService.UserSignUp(dto);
        return ResponseEntity.ok(signupResponseDto);
    }
}
