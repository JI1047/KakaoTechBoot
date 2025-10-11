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
                .join(post.userProfile, userProfile).fetchJoin()//매핑 객체 fetch join 적용
                .join(post.postView, postView).fetchJoin()//매핑 객체 fetch join 적용
                .offset(pageable.getOffset())//pageable 매개변수 통해 offset설정
                .limit(pageable.getPageSize())//pageable 매개변수 통해 limit설정
                .fetch();
        return posts;
    }
}
