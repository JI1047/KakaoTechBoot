package com.example.post_service.Dto.User;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {

    private String email;
    private String password;
}
