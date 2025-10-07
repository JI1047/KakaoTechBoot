package com.example.postService.dto.user.request;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserPasswordRequestDto {
    //비밀번호 업데이트시 클라이언트에서 서버로 요청에 사용되는 dto
    private String newPassword;//새 비밀번호

    private String confirmPassword;//새 비밀번호 확인


}
