package com.project.ohouclonecoding.repository;

import com.project.ohouclonecoding.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostPostId(Long postId);

    @Modifying
    @Query("update Comment c1 set c1.comment = ?2 where c1.commentId = ?1")
    int updateComment(Long commentId, String comment);
}
