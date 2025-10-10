package com.example.postService.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_view")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//데이터베이스의 자동 증가 컬럼을 위해 IDENTITY방법 사용
    @Column(name = "post_view_id")//db에는 user_id로 저장됨
    private Long id;


    @Column(nullable = false)
    @Builder.Default//Builder로 객체 생성시 null로 생성될 수 있음
    private Integer likeCount=0;//좋아요 수

    @Column(nullable = false)
    @Builder.Default//Builder로 객체 생성시 null로 생성될 수 있음
    private Integer commentCount=0;//댓글 수

    @Column(nullable = false)
    @Builder.Default//Builder로 객체 생성시 null로 생성될 수 있음
    private Integer lookCount=0;//조회 수

    public void likeCountIncrease() {
        likeCount+=1;
    }
    public void likeCountDecrease() {
        likeCount-=1;
    }
    public void commentCountIncrease() {
        commentCount+=1;
    }
    public void commentCountDecrease() {
        commentCount-=1;
    }
    public void lookCountUpdate() {
        lookCount+=1;
    }


}
