package com.project.ohouclonecoding.dto;

import com.project.ohouclonecoding.entity.Post;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostResponseDto {
    private Long postId;
    private String nickname;
    private String content;
    private String postImg;



    public PostResponseDto(Post savedPost) {
        this.postId = savedPost.getPostId();
        this.nickname = savedPost.getNickname();
        this.content = savedPost.getContent();
        this.postImg = savedPost.getPostImg();
    }
}
