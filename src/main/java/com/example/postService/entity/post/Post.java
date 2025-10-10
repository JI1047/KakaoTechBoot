package com.example.postService.entity.post;

import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import com.example.postService.entity.BaseTime;
import com.example.postService.entity.comment.Comment;
import com.example.postService.entity.user.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)//좋아요,조회,댓글 수 엔터티 외래 키로 선언
    @JoinColumn(name = "post_view_id")
    private PostView postView;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)//내용,사진 엔터티 외래 키로 선언
    @JoinColumn(name = "post_content_id")
    private PostContent postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")//사용자와 ManyToOne 매핑 외래 키 선언
    private UserProfile userProfile;

    @Column(nullable = false, length = 26)
    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default//Builder로 객체 생성시 null이 될 수도있다.
    private List<Comment> comments = new ArrayList<>();



    public void updatePost(UpdatePostRequestDto dto) {
        this.title = dto.getTitle();
    }//제목 업데이트 메서드

}
