package com.example.postService.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserProfileRequestDto {
    //닉네임,프로필이미지 업데이트시 클라이언트에서 서버로 요청에 사용되는 dto
    private String nickname; //사용자 닉네임

    private String profileImage;//사용자 프로필 이미지


}
