package com.project.ohouclonecoding.repository;

import com.project.ohouclonecoding.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsLikeByPostAndUser(Post post, User user);
    Optional<PostLike> findByPostAndUser(Post post, User user);
}
