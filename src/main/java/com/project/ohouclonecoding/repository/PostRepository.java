package com.project.ohouclonecoding.repository;

import com.project.ohouclonecoding.entity.Post;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
