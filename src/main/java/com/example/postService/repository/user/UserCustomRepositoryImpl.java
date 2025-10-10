package com.example.postService.repository.user;

import com.example.postService.entity.user.QUser;
import com.example.postService.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    QUser user = QUser.user;

    @Override
    public Optional<User> findByEmailWithStaticImport(String email){
        User foundUser = queryFactory
                .selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne();
        return  Optional.ofNullable(foundUser);
    }



}
