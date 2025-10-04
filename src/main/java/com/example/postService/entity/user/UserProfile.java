package com.example.postService.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
@Getter
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//데이터베이스의 자동 증가 컬럼을 위해 IDENTITY방법 사용
    @Column(name = "user_id")//db에는 user_id로 저장됨
    private Long id;

    @Column(nullable = false, unique = true,length = 10)//NOT NULL,무결성(중복 x)설정,길이 조건 설정
    private String nickname; //사용자 닉네임


    private String profileImage;//사용자 프로필 이미지


    protected UserProfile() {}

    public void updateProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

}