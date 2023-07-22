package com.project.ohouclonecoding.repository;

import com.project.ohouclonecoding.entity.Like;
import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsLikeByPostAndUser(Post post, User user);
    Optional<Like> findByPostAndUser(Post post, User user);
}
