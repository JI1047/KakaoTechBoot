package com.example.postService.dto.user.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession { //TODO: 네이밍 컨벤션 적용 안됨
    private Long userProfileId;

    private String nickname;

    private String profileImage;
}
