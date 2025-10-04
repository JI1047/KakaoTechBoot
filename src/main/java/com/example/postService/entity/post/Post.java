package com.example.postService.entity.post;

import com.example.postService.entity.BaseTime;
import com.example.postService.entity.user.UserProfile;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Post extends BaseTime {
    @Id
    private Long postId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "post_id")
    private PostView post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @Column(nullable = false, length = 26)
    private String title;

    @Column(nullable = false)
    private String text;


    private String postImage;

    protected Post() {}
}
