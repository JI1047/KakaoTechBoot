package com.example.postService.repository.post;

import com.example.postService.entity.post.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCustomRepository {
    List<Post> findListPostQueryDSL(Pageable pageable);
}
