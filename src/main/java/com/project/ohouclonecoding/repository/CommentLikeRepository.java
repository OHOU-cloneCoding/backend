package com.project.ohouclonecoding.repository;

import com.project.ohouclonecoding.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsLikeByCommentAndUser(Comment comment, User user);
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
