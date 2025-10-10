package com.example.postService.repository.user;

import com.example.postService.entity.user.User;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<User> findByEmailWithStaticImport(String email);
}
