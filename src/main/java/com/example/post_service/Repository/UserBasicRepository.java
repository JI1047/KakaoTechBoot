package com.example.post_service.Repository;

import com.example.post_service.Entity.user.UserBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBasicRepository extends JpaRepository<UserBasic,Long> {

    UserBasic save(UserBasic userBasic);
    boolean existsByEmail(String email);

    Optional<UserBasic> findByEmail(String email);
}
