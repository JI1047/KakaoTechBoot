package com.example.post_service.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponseDto {

    private String email;

    private String nickname;

    private String profileImage;


}
