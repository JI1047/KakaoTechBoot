package com.example.postService.entity.post;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "post_view")
@Getter
public class PostView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//데이터베이스의 자동 증가 컬럼을 위해 IDENTITY방법 사용
    @Column(name = "post_id")//db에는 user_id로 저장됨
    private Long id;


    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private Integer commentCount;

    @Column(nullable = false)
    private Integer lookCount;

    protected PostView() {

    }

    public PostView(Integer likeCount, Integer commentCount, Integer lookCount) {
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.lookCount = lookCount;
    }




}
