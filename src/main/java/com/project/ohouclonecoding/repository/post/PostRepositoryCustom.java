package com.project.ohouclonecoding.repository.post;

import com.project.ohouclonecoding.dto.PostSearchDto;
import com.project.ohouclonecoding.dto.post.AllPostResponseDto;
import org.springframework.data.domain.Page;

public interface PostRepositoryCustom {

    Page<AllPostResponseDto> searchPosts(PostSearchDto condition);
}
