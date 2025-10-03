package com.example.post_service.Entity.post;

import com.example.post_service.Entity.BaseTime;
import com.example.post_service.Entity.user.UserBasic;
import com.example.post_service.Entity.user.UserExtra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_basic")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PostBasic {
    @Id
    private Long postId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "post_id")
    private PostExtra post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserExtra user;

    @Column(nullable = false, length = 26)
    private String title;

    @Column(nullable = false)
    private String text;


    private String postImage;

    @Embedded
    private BaseTime baseTime;

    protected PostBasic() {}

    public PostBasic(PostExtra post, UserExtra user, String title, String text, String postImage) {
        this.post = post;
        this.user = user;
        this.title = title;
        this.text = text;
        this.postImage = postImage;
    }


}
