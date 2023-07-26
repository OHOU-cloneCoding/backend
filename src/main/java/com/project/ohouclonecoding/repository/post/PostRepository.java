package com.project.ohouclonecoding.repository.post;

import com.project.ohouclonecoding.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long>, PostRepositoryCustom {
    Page<Post> findAllByOrderByModifiedAtDesc(Pageable pageable);

}
