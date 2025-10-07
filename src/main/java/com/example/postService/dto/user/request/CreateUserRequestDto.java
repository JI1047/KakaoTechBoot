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
    //회원가입시 클라이언트에서 서버로 요청에 사용되는 dto

    private String email;//입력받은 이메일

    private String nickname;//입력받은 닉네임

    private String password;//입력받은 비밀번호

    private String confirmPassword;//입력받은 비밀번호 확인(DB에는 저장안됨)

    private String profileImage;//입력받은 s3.Url


}

