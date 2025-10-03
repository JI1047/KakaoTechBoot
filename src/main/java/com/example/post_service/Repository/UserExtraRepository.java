package com.example.post_service.Repository;

import com.example.post_service.Entity.user.UserExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {

    boolean existsByNickname(String nickname);
}
