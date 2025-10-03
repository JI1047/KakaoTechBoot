package com.example.post_service.Dto.User;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String email;

    private String nickname;

    private String password;

    private String confirmPassword;

    private String profileImage;


}
