package com.example.postService.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDto {

    private String email;

    private String nickname;

    private String password;

    private String confirmPassword;

    private String profileImage;


}
