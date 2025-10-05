package com.example.postService.repository.post;

import com.example.postService.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
}
