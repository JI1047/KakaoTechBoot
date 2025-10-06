package com.example.postService.entity.comment;

import com.example.postService.entity.BaseTime;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "comment")
@Getter
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}