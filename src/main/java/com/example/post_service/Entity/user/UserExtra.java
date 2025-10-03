package com.example.post_service.Entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_extra")
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
@Getter
@Builder
public class UserExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//데이터베이스의 자동 증가 컬럼을 위해 IDENTITY방법 사용
    @Column(name = "user_id")//db에는 user_id로 저장됨
    private Long id;

    @Column(nullable = false, unique = true,length = 10)//NOT NULL,무결성(중복 x)설정,길이 조건 설정
    private String nickname; //사용자 닉네임


    private String profileImage;//사용자 프로필 이미지


    protected UserExtra() {}
    public UserExtra(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}