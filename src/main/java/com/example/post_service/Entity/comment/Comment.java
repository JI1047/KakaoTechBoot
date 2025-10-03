package com.example.post_service.Entity.comment;

import com.example.post_service.Entity.BaseTime;
import com.example.post_service.Entity.post.PostBasic;
import com.example.post_service.Entity.user.UserBasic;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String text;

    @Embedded
    private BaseTime baseTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserBasic user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostBasic post;

}