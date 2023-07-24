package com.project.ohouclonecoding.repository;

import com.project.ohouclonecoding.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
}
