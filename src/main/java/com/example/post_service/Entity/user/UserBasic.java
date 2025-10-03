package com.example.post_service.Entity.user;

import com.example.post_service.Entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_basic")//db 테이블명 설정
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
public class UserBasic {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    //unique = true로 1ㄷ1보장을 하려했으나 PK,FK가 공유되어야하기 때문에 @MapsId를 사용했습니다.
    @JoinColumn(name = "user_id")
    private UserExtra user;


    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;
    //비밀번호 확인 값 애플리케이션 내부 로직에서만 필요한 값

    @Embedded
    private BaseTime baseTime;

    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean isDeleted;//boolean형은 기본 default값이 false이기 때문에 설정 x




}
//사용자의 닉네임 or 프로필 이미지를 통해서 이메일이나,패스워드를 조회하는 일은 거의 없기 때문에 단방향으로 설정함