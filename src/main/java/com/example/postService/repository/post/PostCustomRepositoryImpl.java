package com.example.postService.repository.post;

import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.QPost;
import com.example.postService.entity.post.QPostView;
import com.example.postService.entity.user.QUserProfile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    QPost post = QPost.post;

    QUserProfile userProfile = QUserProfile.userProfile;

    QPostView postView = QPostView.postView;

    @Override
    public List<Post> findListPostQueryDSL(Pageable pageable) {
        List<Post> posts = queryFactory
                .selectFrom(post)
                .join(post.userProfile, userProfile).fetchJoin()
                .join(post.postView, postView).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return posts;
    }
}
