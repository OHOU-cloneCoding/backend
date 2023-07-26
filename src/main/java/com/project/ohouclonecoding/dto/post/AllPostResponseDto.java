package com.project.ohouclonecoding.dto.post;

import com.project.ohouclonecoding.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllPostResponseDto {
    private Long postId;
    private String nickname;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private int likeSize;
    private int commentSize;
    private long postViewCount;



    public AllPostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getNickname();
        this.content = post.getContent();
        this.postImg = post.getPostImg();
        this.createdAt = post.getCreatedAt();
        this.likeSize = post.getLike().size();
        this.commentSize = post.getCommentList().size();
        this.postViewCount = post.getPostViewCount();
    }
}
