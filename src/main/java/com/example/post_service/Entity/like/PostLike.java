package com.example.post_service.Entity.like;

import com.example.post_service.Entity.post.PostBasic;
import com.example.post_service.Entity.user.UserBasic;
import jakarta.persistence.*;

@Entity
@Table(name = "post_like")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserBasic user;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private PostBasic post;
}
