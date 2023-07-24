package com.project.ohouclonecoding.repository.post;

import com.project.ohouclonecoding.dto.PostSearchDto;
import com.project.ohouclonecoding.dto.post.AllPostResponseDto;
import com.project.ohouclonecoding.entity.Post;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        Post post = new Post(null, "nick1", "content1", "abc");
        Post post2 = new Post(null, "nick2", "content2", "abc");
        Post post3 = new Post(null, "nick3", "congtent3", "abc");
        Post post4 = new Post(null, "nick4", "content4", "abc");
        Post post5 = new Post(null, "nick5", "contengt5", "abc");
        Post post6 = new Post(null, "nick6", "content6", "abc");

        List<Post> postList = new ArrayList<>(Arrays.asList(post, post2, post3, post4, post5, post6));
        System.out.println("postList.size() = " + postList.size());

        postRepository.saveAll(postList);

        em.flush();
        em.clear();
    }

    @Test
    public void search() throws Exception {
        // given
        PostSearchDto condition = PostSearchDto.builder()
                .keyword("g")
                .orderField("createdAt")
                .isASC(false)
                .page(1)
                .size(3)
                .build();


        Page<AllPostResponseDto> result = postRepository.searchPosts(condition);
        for (AllPostResponseDto allPostResponseDto : result) {
            System.out.println("allPostResponseDto.getContent() = " + allPostResponseDto.getContent());
        }

        // when
//        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getTotalElements()).isEqualTo(2);

        // then

    }
}